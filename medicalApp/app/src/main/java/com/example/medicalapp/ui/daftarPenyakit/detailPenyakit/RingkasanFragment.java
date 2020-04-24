package com.example.medicalapp.ui.daftarPenyakit.detailPenyakit;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalapp.models.Penyakit;
import com.example.medicalapp.R;

public class RingkasanFragment extends Fragment implements DetailPenyakitActivity.RingkasanListener {

    //UI Elements
    private ImageView mImageView;
    private TextView mRingkasanTextView;

    //Variables
    private DetailPenyakitActivity mActivity;
    private static final String TAG = "Ringkasan Fragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (DetailPenyakitActivity) getActivity();
        mActivity.setRingkasanListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ringkasan, container, false);
        mImageView = root.findViewById(R.id.image_penyakit);
        mRingkasanTextView = root.findViewById(R.id.ringkasan);
        mActivity.sendPenyakit();
        return root;
    }

    @Override
    public void onDataReceived(Penyakit penyakit) {
        if (getActivity().getPackageName() != null) {
            int resourceID = getResources().getIdentifier(penyakit.getImage(), "drawable", getActivity().getPackageName());
            mImageView.setImageResource(resourceID);
        } else {
            Log.d(TAG, "getActivity().getPackageName() returns null");
        }
        mRingkasanTextView.setText(penyakit.getRingkasan());
    }
}
