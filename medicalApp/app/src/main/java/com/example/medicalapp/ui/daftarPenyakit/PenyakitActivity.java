package com.example.medicalapp.ui.daftarPenyakit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.medicalapp.Models.Penyakit;
import com.example.medicalapp.R;

public class PenyakitActivity extends AppCompatActivity {

    //UI Components
    private TextView mViewNamaPenyakit;

    //Variables
    private Penyakit mPenyakit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit);

        mViewNamaPenyakit = findViewById(R.id.nama_penyakit);

        mPenyakit = getIntent().getParcelableExtra("penyakit_terpilih");
        setPenyakitProperties();
    }

    private void setPenyakitProperties(){
        mViewNamaPenyakit.setText(mPenyakit.getNamaPenyakit());
    }
}
