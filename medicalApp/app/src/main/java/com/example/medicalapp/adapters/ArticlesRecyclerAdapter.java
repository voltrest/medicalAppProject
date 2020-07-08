package com.example.medicalapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalapp.R;
import com.example.medicalapp.models.Article;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Article> mArticleList;
    private OnArticleListener mOnArticleListener;

    public ArticlesRecyclerAdapter(ArrayList<Article> articleList, OnArticleListener onArticleListener) {
        this.mArticleList = articleList;
        this.mOnArticleListener = onArticleListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageView image;
        ArticlesRecyclerAdapter.OnArticleListener onArticleListener;
        public MyViewHolder(@NonNull View view, OnArticleListener onArticleListener) {
            super(view);
            title = view.findViewById(R.id.article_list_item_title);
            image = view.findViewById(R.id.article_list_item_image);
            this.onArticleListener = onArticleListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onArticleListener.onArticleClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
        return new ArticlesRecyclerAdapter.MyViewHolder(view, mOnArticleListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.title.setText(mArticleList.get(position).getJudul());
        String tempImage = mArticleList.get(position).getGambarArtikel();
        if (tempImage != null){
            byte[] decodedString = Base64.decode(tempImage, Base64.NO_WRAP);
            InputStream inputStream  = new ByteArrayInputStream(decodedString);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            myViewHolder.image.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public interface OnArticleListener {
        void onArticleClick(int position);
    }
}
