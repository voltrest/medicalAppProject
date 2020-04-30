package com.example.medicalapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalapp.R;

import java.util.HashMap;
import java.util.List;

public class DaftarRumahSakitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HashMap<String, String>> mDaftarRumahSakit;
    private OnRumahSakitListener mOnRumahSakitListener;

    public DaftarRumahSakitAdapter(List<HashMap<String, String>> daftarRumahSakit, OnRumahSakitListener onRumahSakitListener) {
        this.mDaftarRumahSakit = daftarRumahSakit;
        this.mOnRumahSakitListener = onRumahSakitListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView namaRumahSakit;
        OnRumahSakitListener onRumahSakitListener;
        public MyViewHolder(@NonNull View view, OnRumahSakitListener onRumahSakitListener) {
            super(view);
            namaRumahSakit = view.findViewById(R.id.nama_rumah_sakit);
            this.onRumahSakitListener = onRumahSakitListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRumahSakitListener.onRumahSakitClick(getAdapterPosition());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_rumah_sakit_list_item, parent, false);
        return new MyViewHolder(view, mOnRumahSakitListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.namaRumahSakit.setText(mDaftarRumahSakit.get(position).get("place_name"));
    }

    @Override
    public int getItemCount() {
        return mDaftarRumahSakit.size();
    }

    public interface OnRumahSakitListener {
        void onRumahSakitClick(int position);
    }
}
