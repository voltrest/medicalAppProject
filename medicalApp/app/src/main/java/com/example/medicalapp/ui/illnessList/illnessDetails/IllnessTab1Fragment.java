package com.example.medicalapp.ui.illnessList.illnessDetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalapp.models.Illness;
import com.example.medicalapp.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class IllnessTab1Fragment extends Fragment implements IllnessDetailActivity.IllnessTab1Listener {

    //UI Elements
    private ImageView mImageView;
    private TextView mRingkasanTextView;
    private TextView mPenyebabTextView;
    private TextView mGejalaTextView;

    //Variables
    private IllnessDetailActivity mActivity;
    private static final String TAG = "Ringkasan Fragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (IllnessDetailActivity) getActivity();
        mActivity.setIllnessTab1Listener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_illness_tab1, container, false);
        mImageView = root.findViewById(R.id.image_illness);
        mRingkasanTextView = root.findViewById(R.id.summary);
        mPenyebabTextView = root.findViewById(R.id.cause);
        mGejalaTextView = root.findViewById(R.id.symptom);
        mActivity.sendIllness();
        return root;
    }

    @Override
    public void onDataReceived(Illness illness) {
//        if (getActivity().getPackageName() != null) {
//            int resourceID = getResources().getIdentifier(illness.getGambarPenyakit(), "drawable", getActivity().getPackageName());
//            mImageView.setImageResource(resourceID);
//        } else {
//            Log.d(TAG, "getActivity().getPackageName() returns null");
//        }

//        byte[] decodedString = Base64.decode(illness.getGambarPenyakit(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        InputStream inputStream = new ByteArrayInputStream(decodedString);
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//        mImageView.setImageBitmap(bitmap);

        byte[] decodedString = Base64.decode(illness.getGambarPenyakit(), Base64.NO_WRAP);
        InputStream inputStream  = new ByteArrayInputStream(decodedString);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        mImageView.setImageBitmap(bitmap);

        mRingkasanTextView.setText(illness.getRingkasan());
        mPenyebabTextView.setText(illness.getPenyebab());
        mGejalaTextView.setText(illness.getGejala());
    }
}
