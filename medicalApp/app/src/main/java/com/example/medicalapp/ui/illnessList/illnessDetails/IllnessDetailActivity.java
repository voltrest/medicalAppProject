package com.example.medicalapp.ui.illnessList.illnessDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.medicalapp.adapters.IllnessDetailsTabsAdapter;
import com.example.medicalapp.models.Illness;
import com.example.medicalapp.R;
import com.google.android.material.tabs.TabLayout;

public class IllnessDetailActivity extends AppCompatActivity {

    //UI Components
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    //Variables
    private Illness mIllness;
    private IllnessDetailsTabsAdapter mIllnessDetailsTabsAdapter;
    private IllnessTab1Listener mIllnessTab1Listener;
    private IllnessTab2Listener mIllnessTab2Listener;
    private IllnessTab3Listener mIllnessTab3Listener;
    private static final String TAG = "IllnessDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illness_details);

        mIllness = getIntent().getParcelableExtra("chosen illness");
        setIllnessProperties();

        mIllnessDetailsTabsAdapter = new IllnessDetailsTabsAdapter(getSupportFragmentManager());

        mViewPager =  findViewById(R.id.container);
        setupViewPager(mViewPager);

        mTabLayout = findViewById(R.id.tabs_illness_details);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setIllnessProperties(){
        setTitle(mIllness.getNamaPenyakit());
        Log.d(TAG, "DETAIL PENYAKIT");
        Log.d(TAG, "name: " + mIllness.getNamaPenyakit());
        Log.d(TAG, "image: " + mIllness.getGambarPenyakit());
        Log.d(TAG, "ringkasan: " + mIllness.getRingkasan());
        Log.d(TAG, "penyebab: " + mIllness.getPenyebab());
        Log.d(TAG, "gejala: " + mIllness.getGejala());
        Log.d(TAG, "diagnosa: " + mIllness.getDiagnosa());
//        Log.d("tag", "setPenyakitProperties: getNamaPenyakit: " + mPenyakit.getNamaPenyakit());
//        Log.d("tag", "setPenyakitProperties: getRingkasan: " + mPenyakit.getRingkasan());
//        Log.d("tag", "setPenyakitProperties: getImage: " + mPenyakit.getImage());
    }

    private void setupViewPager(ViewPager viewPager) {
        IllnessDetailsTabsAdapter adapter = new IllnessDetailsTabsAdapter(getSupportFragmentManager());
        adapter.addFragment(new IllnessTab1Fragment(), "Penyebab & Gejala");
        adapter.addFragment(new IllnessTab2Fragment(), "Diagnosa & Pencegahan");
        adapter.addFragment(new IllnessTab3Fragment(), "Spesialis");
        viewPager.setAdapter(adapter);
    }

    public interface IllnessTab1Listener {
        void onDataReceived(Illness illness);
    }

    public void setIllnessTab1Listener(IllnessTab1Listener illnessTab1Listener){
        this.mIllnessTab1Listener = illnessTab1Listener;
    }

    public void sendIllness(){
        mIllnessTab1Listener.onDataReceived(mIllness);
    }

    public interface IllnessTab2Listener {
        void onDataReceived(Illness illness);
    }

    public void setIllnessTab2Listener(IllnessTab2Listener illnessTab2Listener){
        this.mIllnessTab2Listener = illnessTab2Listener;
    }

    public void sendIllnessTab2(){
        mIllnessTab2Listener.onDataReceived(mIllness);
    }

    public interface IllnessTab3Listener {
        void onDataReceived(Illness illness);
    }

    public void setPenyakitTab3Listener(IllnessTab3Listener illnessTab3Listener){
        this.mIllnessTab3Listener = illnessTab3Listener;
    }

    public void sendPenyakitTab3(){
        mIllnessTab3Listener.onDataReceived(mIllness);
    }
}
