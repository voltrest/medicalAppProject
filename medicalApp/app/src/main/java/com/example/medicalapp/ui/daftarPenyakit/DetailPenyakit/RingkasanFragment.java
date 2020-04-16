package com.example.medicalapp.ui.daftarPenyakit.DetailPenyakit;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medicalapp.Models.Penyakit;
import com.example.medicalapp.R;

public class RingkasanFragment extends Fragment implements DetailPenyakitActivity.RingkasanListener {

    //UI Elements
    private TextView mRingkasanTextView;

    //Variables
    private DetailPenyakitActivity mActivity;

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
        mRingkasanTextView = root.findViewById(R.id.ringkasan);
        mActivity.sendPenyakit();
        return root;
    }

    @Override
    public void onDataReceived(Penyakit penyakit) {
        mRingkasanTextView.setText(penyakit.getRingkasan());
    }
}
