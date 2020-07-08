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

public class IllnessTab2Fragment extends Fragment implements IllnessDetailActivity.IllnessTab2Listener {

    //UI Elements
    private TextView mDiagnoseText;
    private TextView mPreventionText;

    //Variables
    private IllnessDetailActivity mActivity;
    private static final String TAG = "Detail Penyakit Tab 2";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (IllnessDetailActivity) getActivity();
        mActivity.setIllnessTab2Listener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_illness_tab2, container, false);
        mDiagnoseText = root.findViewById(R.id.diagnosa);
        mPreventionText = root.findViewById(R.id.pencegahan);
        mActivity.sendIllnessTab2();
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
            mDiagnoseText.setText(illness.getDiagnosa());
            mPreventionText.setText(illness.getPencegahan());
        } else {
            Log.e(TAG, "onDataReceived: Diagnosa is null");
        }
    }
}
