package com.example.medicalapp.ui.daftarPenyakit.detailPenyakit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.medicalapp.adapters.DetailPenyakitTabsAdapter;
import com.example.medicalapp.models.Penyakit;
import com.example.medicalapp.R;
import com.google.android.material.tabs.TabLayout;

public class DetailPenyakitActivity extends AppCompatActivity {

    //UI Components
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    //Variables
    private Penyakit mPenyakit;
    private DetailPenyakitTabsAdapter mDetailPenyakitTabsAdapter;
    private RingkasanListener mRingkasanListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyakit);

        mPenyakit = getIntent().getParcelableExtra("penyakit_terpilih");
        setPenyakitProperties();

        mDetailPenyakitTabsAdapter = new DetailPenyakitTabsAdapter(getSupportFragmentManager());

        mViewPager =  findViewById(R.id.container);
        setupViewPager(mViewPager);

        mTabLayout = findViewById(R.id.tabs_detail_penyakit);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setPenyakitProperties(){
        setTitle(mPenyakit.getNamaPenyakit());
        Log.d("tag", "setPenyakitProperties: getNamaPenyakit: " + mPenyakit.getNamaPenyakit());
        Log.d("tag", "setPenyakitProperties: getRingkasan: " + mPenyakit.getRingkasan());
        Log.d("tag", "setPenyakitProperties: getImage: " + mPenyakit.getImage());
    }

    private void setupViewPager(ViewPager viewPager) {
//        DetailPenyakitTabsAdapter adapter = new DetailPenyakitTabsAdapter(getSupportFragmentManager());
        DetailPenyakitTabsAdapter adapter = new DetailPenyakitTabsAdapter(getSupportFragmentManager());
        adapter.addFragment(new RingkasanFragment(), "Penyebab & Gejala");
        adapter.addFragment(new GejalaFragment(), "Diagnosa & Pencegahan");
        adapter.addFragment(new GejalaFragment(), "Spesialis");
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
