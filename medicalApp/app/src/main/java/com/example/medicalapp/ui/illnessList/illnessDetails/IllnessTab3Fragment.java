package com.example.medicalapp.ui.illnessList.illnessDetails;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.medicalapp.R;
import com.example.medicalapp.models.Illness;

public class IllnessTab3Fragment extends Fragment implements IllnessDetailActivity.IllnessTab3Listener {

    //UI Elements
    private TextView mSpecialistText;

    //Variables
    private IllnessDetailActivity mActivity;
    private static final String TAG = "Detail Penyakit Tab 3";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (IllnessDetailActivity) getActivity();
        mActivity.setPenyakitTab3Listener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_illness_tab3, container, false);
        mSpecialistText = root.findViewById(R.id.spesialis);
        mActivity.sendPenyakitTab3();
        return root;
    }

    @Override
    public void onDataReceived(Illness illness) {
        if (illness.getDiagnosa() != null){
            Log.d(TAG, "PENYAKIT TAB 2");
            Log.d(TAG, "name: " + illness.getNamaPenyakit());
            Log.d(TAG, "image: " + illness.getGambarPenyakit());
            Log.d(TAG, "ringkasan: " + illness.getRingkasan());
            Log.d(TAG, "penyebab: " + illness.getPenyebab());
            Log.d(TAG, "gejala: " + illness.getGejala());
            Log.d(TAG, "diagnosa: " + illness.getDiagnosa());
            mSpecialistText.setText(illness.getDiagnosa());
        } else {
            Log.e(TAG, "onDataReceived: Diagnosa is null");
        }
    }
}
