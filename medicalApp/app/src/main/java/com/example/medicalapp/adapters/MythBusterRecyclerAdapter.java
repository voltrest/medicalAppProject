package com.example.medicalapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalapp.R;
import com.example.medicalapp.models.Myth;

import java.util.ArrayList;

public class MythBusterRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Myth> mMythList;

    private ArrayList<Integer> mColorList;;
    private int mColorIndex;

    public MythBusterRecyclerAdapter(ArrayList<Myth> mythList, ArrayList<Integer> colorList) {
        this.mMythList = mythList;
        this.mColorList = colorList;
        mColorIndex = 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView question;
        public TextView answer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_myth);
            question = itemView.findViewById(R.id.text_question);
            answer = itemView.findViewById(R.id.text_answer);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myth_buster_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.question.setText(mMythList.get(position).getPertanyaan());
        myViewHolder.answer.setText(mMythList.get(position).getJawaban());

        myViewHolder.cardView.setCardBackgroundColor(mColorList.get(mColorIndex));
        if (mColorIndex == mColorList.size() - 1){
            mColorIndex = 0;
        } else {
            mColorIndex++;
        }
    }

    @Override
    public int getItemCount() {
        return mMythList.size();
    }
}
