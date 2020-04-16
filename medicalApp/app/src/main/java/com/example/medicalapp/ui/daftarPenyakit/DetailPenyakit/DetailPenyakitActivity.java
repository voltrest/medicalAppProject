package com.example.medicalapp.ui.daftarPenyakit.DetailPenyakit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalapp.Adapters.DetailPenyakitTabsAdapter;
import com.example.medicalapp.Models.Penyakit;
import com.example.medicalapp.R;
import com.google.android.material.tabs.TabLayout;

public class DetailPenyakitActivity extends AppCompatActivity {

    //UI Components
    private TextView mTextView;
    private ImageView mImageView;

    //Variables
    private Penyakit mPenyakit;
    private DetailPenyakitTabsAdapter mDetailPenyakitTabsAdapter;
    private ViewPager mViewPager;
    private RingkasanListener mRingkasanListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyakit);

        mTextView = findViewById(R.id.nama_penyakit);
        mImageView = findViewById(R.id.image_penyakit);

        mPenyakit = getIntent().getParcelableExtra("penyakit_terpilih");
        setPenyakitProperties();

        mDetailPenyakitTabsAdapter = new DetailPenyakitTabsAdapter(getSupportFragmentManager());

        mViewPager =  findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setPenyakitProperties(){
        mTextView.setText(mPenyakit.getNamaPenyakit());
        int resourceID = getResources().getIdentifier(mPenyakit.getImage(), "drawable", this.getPackageName());
        Log.d("tag", "setPenyakitProperties: getNamaPenyakit: " + mPenyakit.getNamaPenyakit());
        Log.d("tag", "setPenyakitProperties: getRingkasan: " + mPenyakit.getRingkasan());
        Log.d("tag", "setPenyakitProperties: getImage: " + mPenyakit.getImage());
        mImageView.setImageResource(resourceID);
    }

    private void setupViewPager(ViewPager viewPager) {
        DetailPenyakitTabsAdapter adapter = new DetailPenyakitTabsAdapter(getSupportFragmentManager());
        adapter.addFragment(new RingkasanFragment(), "Ringkasan");
        adapter.addFragment(new GejalaFragment(), "Gejala");
        viewPager.setAdapter(adapter);
    }

    public interface RingkasanListener {
        void onDataReceived(Penyakit penyakit);
    }

    public void setRingkasanListener(RingkasanListener ringkasanListener){
        this.mRingkasanListener = ringkasanListener;
    }

    public void sendPenyakit(){
        mRingkasanListener.onDataReceived(mPenyakit);
    }
}
