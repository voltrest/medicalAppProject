package com.example.medicalapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalapp.Models.Penyakit;
import com.example.medicalapp.R;

import java.util.ArrayList;

public class DaftarPenyakitAdapter extends RecyclerView.Adapter<DaftarPenyakitAdapter.MyViewHolder> {
//    private String[] mDataset;
    private ArrayList<Penyakit> mDaftarPenyakit = new ArrayList<>();
    private OnPenyakitListener mOnPenyakitListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView namaPenyakit;
        OnPenyakitListener onPenyakitListener;
        public MyViewHolder(@NonNull View itemView, OnPenyakitListener onPenyakitListener) {
            super(itemView);
            namaPenyakit = itemView.findViewById(R.id.nama_penyakit);
            this.onPenyakitListener = onPenyakitListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPenyakitListener.onPenyakitClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
//    public DaftarPenyakitAdapter(String[] myDataset) {
//        mDataset = myDataset;
//    }
    public DaftarPenyakitAdapter(ArrayList<Penyakit> daftarPenyakit, OnPenyakitListener onPenyakitListener) {
        this.mDaftarPenyakit = daftarPenyakit;
        this.mOnPenyakitListener = onPenyakitListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DaftarPenyakitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_penyakit_list_item, parent, false);
        return new MyViewHolder(view, mOnPenyakitListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        viewHolder.namaPenyakit.setText(mDaftarPenyakit.get(position).getNamaPenyakit());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDaftarPenyakit.size();
    }

    public interface OnPenyakitListener{
        void onPenyakitClick(int position);
    }
}
