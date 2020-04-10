package com.example.medicalapp.ui.daftarPenyakit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicalapp.R;

public class DaftarPenyakitFragment extends Fragment {

    private DaftarPenyakitViewModel daftarPenyakitViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        daftarPenyakitViewModel =
                ViewModelProviders.of(this).get(DaftarPenyakitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_daftar_penyakit, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        daftarPenyakitViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}