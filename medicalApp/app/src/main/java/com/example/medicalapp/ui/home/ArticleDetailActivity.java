package com.example.medicalapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalapp.R;
import com.example.medicalapp.models.Article;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class ArticleDetailActivity extends AppCompatActivity {
    //UI Components
    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mBodyTextView;
    private TextView mWriterTextView;
    private TextView mDateWrittenTextView;
    private TextView mDateCopiedTextView;

    //Variables
    private Article mArticle;
    private static final String TAG = "ArticleDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        mTitleTextView = findViewById(R.id.article_title);
        mImageView = findViewById(R.id.image_article);
        mBodyTextView = findViewById(R.id.body);
        mWriterTextView = findViewById(R.id.writer);
        mDateWrittenTextView = findViewById(R.id.date_written);
        mDateCopiedTextView = findViewById(R.id.date_copied);

        mArticle = getIntent().getParcelableExtra("chosen article");
        setArticleProperties();
    }

    private void setArticleProperties(){
        mTitleTextView.setText(mArticle.getJudul());

        byte[] decodedString = Base64.decode(mArticle.getGambarArtikel(), Base64.NO_WRAP);
        InputStream inputStream  = new ByteArrayInputStream(decodedString);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        mImageView.setImageBitmap(bitmap);

        mBodyTextView.setText(mArticle.getBadan());
        mWriterTextView.setText(mArticle.getPenulis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        mDateWrittenTextView.setText(dateFormat.format(mArticle.getTanggalTulis()));
        mDateCopiedTextView.setText(dateFormat.format(mArticle.getTanggalCopy()));
    }
}
