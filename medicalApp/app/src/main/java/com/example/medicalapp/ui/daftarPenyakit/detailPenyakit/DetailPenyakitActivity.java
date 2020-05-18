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
    private PenyakitTab1Listener mPenyakitTab1Listener;
    private PenyakitTab2Listener mPenyakitTab2Listener;
    private PenyakitTab3Listener mPenyakitTab3Listener;
    private static final String TAG = "DetailPenyakitActivity";

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
        Log.d(TAG, "DETAIL PENYAKIT");
        Log.d(TAG, "name: " + mPenyakit.getNamaPenyakit());
        Log.d(TAG, "image: " + mPenyakit.getImage());
        Log.d(TAG, "ringkasan: " + mPenyakit.getRingkasan());
        Log.d(TAG, "penyebab: " + mPenyakit.getPenyebab());
        Log.d(TAG, "gejala: " + mPenyakit.getGejala());
        Log.d(TAG, "diagnosa: " + mPenyakit.getDiagnosa());
//        Log.d("tag", "setPenyakitProperties: getNamaPenyakit: " + mPenyakit.getNamaPenyakit());
//        Log.d("tag", "setPenyakitProperties: getRingkasan: " + mPenyakit.getRingkasan());
//        Log.d("tag", "setPenyakitProperties: getImage: " + mPenyakit.getImage());
    }

    private void setupViewPager(ViewPager viewPager) {
        DetailPenyakitTabsAdapter adapter = new DetailPenyakitTabsAdapter(getSupportFragmentManager());
        adapter.addFragment(new PenyakitTab1Fragment(), "Penyebab & Gejala");
        adapter.addFragment(new PenyakitTab2Fragment(), "Diagnosa & Pencegahan");
        adapter.addFragment(new PenyakitTab3Fragment(), "Spesialis");
        viewPager.setAdapter(adapter);
    }

    public interface PenyakitTab1Listener {
        void onDataReceived(Penyakit penyakit);
    }

    public void setPenyakitTab1Listener(PenyakitTab1Listener penyakitTab1Listener){
        this.mPenyakitTab1Listener = penyakitTab1Listener;
    }

    public void sendPenyakit(){
        mPenyakitTab1Listener.onDataReceived(mPenyakit);
    }

    public interface PenyakitTab2Listener {
        void onDataReceived(Penyakit penyakit);
    }

    public void setPenyakitTab2Listener(PenyakitTab2Listener penyakitTab2Listener){
        this.mPenyakitTab2Listener = penyakitTab2Listener;
    }

    public void sendPenyakitTab2(){
        mPenyakitTab2Listener.onDataReceived(mPenyakit);
    }

    public interface PenyakitTab3Listener {
        void onDataReceived(Penyakit penyakit);
    }

    public void setPenyakitTab3Listener(PenyakitTab3Listener penyakitTab3Listener){
        this.mPenyakitTab3Listener = penyakitTab3Listener;
    }

    public void sendPenyakitTab3(){
        mPenyakitTab3Listener.onDataReceived(mPenyakit);
    }
}
