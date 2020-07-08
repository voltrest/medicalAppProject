package com.example.medicalapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalapp.R;

import java.util.HashMap;
import java.util.List;

public class HospitalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HashMap<String, String>> mHospitalList;
    private OnHospitalListener mOnHospitalListener;

    public HospitalRecyclerAdapter(List<HashMap<String, String>> daftarRumahSakit, OnHospitalListener onHospitalListener) {
        this.mHospitalList = daftarRumahSakit;
        this.mOnHospitalListener = onHospitalListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView hospitalName;
        OnHospitalListener onHospitalListener;
        private MyViewHolder(@NonNull View view, OnHospitalListener onHospitalListener) {
            super(view);
            hospitalName = view.findViewById(R.id.nama_rumah_sakit);
            this.onHospitalListener = onHospitalListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onHospitalListener.onHospitalClick(getAdapterPosition());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_list_item, parent, false);
        return new MyViewHolder(view, mOnHospitalListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.hospitalName.setText(mHospitalList.get(position).get("name"));
    }

    @Override
    public int getItemCount() {
        return mHospitalList.size();
    }

    public interface OnHospitalListener {
        void onHospitalClick(int position);
    }
}
