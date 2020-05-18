package com.example.medicalapp.ui.daftarPenyakit.detailPenyakit;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.medicalapp.R;
import com.example.medicalapp.models.Penyakit;

public class PenyakitTab3Fragment extends Fragment implements DetailPenyakitActivity.PenyakitTab3Listener {

    //UI Elements
    private TextView mSpesialisText;

    //Variables
    private DetailPenyakitActivity mActivity;
    private static final String TAG = "Detail Penyakit Tab 3";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (DetailPenyakitActivity) getActivity();
        mActivity.setPenyakitTab3Listener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_penyakit_tab3, container, false);
        mSpesialisText = root.findViewById(R.id.spesialis);
        mActivity.sendPenyakitTab3();
        return root;
    }

    @Override
    public void onDataReceived(Penyakit penyakit) {
        if (penyakit.getDiagnosa() != null){
            Log.d(TAG, "PENYAKIT TAB 2");
            Log.d(TAG, "name: " + penyakit.getNamaPenyakit());
            Log.d(TAG, "image: " + penyakit.getImage());
            Log.d(TAG, "ringkasan: " + penyakit.getRingkasan());
            Log.d(TAG, "penyebab: " + penyakit.getPenyebab());
            Log.d(TAG, "gejala: " + penyakit.getGejala());
            Log.d(TAG, "diagnosa: " + penyakit.getDiagnosa());
            mSpesialisText.setText(penyakit.getDiagnosa());
        } else {
            Log.e(TAG, "onDataReceived: Diagnosa is null");
        }
    }
}
