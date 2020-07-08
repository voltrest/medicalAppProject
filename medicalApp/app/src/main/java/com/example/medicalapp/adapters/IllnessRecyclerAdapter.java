package com.example.medicalapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalapp.models.Illness;
import com.example.medicalapp.R;

import java.util.ArrayList;

public class IllnessRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Illness> mIllnessList;
    private OnIllnessListener mOnIllnessListener;

    private static final int HEADER_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public IllnessRecyclerAdapter(ArrayList<Illness> illnessList, OnIllnessListener onIllnessListener) {
        this.mIllnessList = illnessList;
        this.mOnIllnessListener = onIllnessListener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView huruf;
        public HeaderViewHolder(@NonNull View headerView, OnIllnessListener onIllnessListener) {
            super(headerView);
            huruf = headerView.findViewById(R.id.huruf);
        }

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView illnessName;
        OnIllnessListener onIllnessListener;
        public ItemViewHolder(@NonNull View itemView, OnIllnessListener onIllnessListener) {
            super(itemView);
            illnessName = itemView.findViewById(R.id.nama_penyakit);
            this.onIllnessListener = onIllnessListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onIllnessListener.onIllnessClick(getAdapterPosition());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mIllnessList.get(position).isHeader()){
            return HEADER_VIEW;
        } else {
            return ITEM_VIEW;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view;
        if (viewType == HEADER_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.illness_list_header, parent, false);
            return new HeaderViewHolder(view, mOnIllnessListener);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.illness_list_item, parent, false);;
            return new ItemViewHolder(view, mOnIllnessListener);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (getItemViewType(position) == HEADER_VIEW){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            headerViewHolder.huruf.setText(mIllnessList.get(position).getNamaPenyakit());
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            itemViewHolder.illnessName.setText(mIllnessList.get(position).getNamaPenyakit());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mIllnessList.size();
    }

    public interface OnIllnessListener {
        void onIllnessClick(int position);
    }
}
