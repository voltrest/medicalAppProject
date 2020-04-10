package com.example.medicalapp.ui.rumahSakit;

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

public class RumahSakitFragment extends Fragment {

    private RumahSakitViewModel rumahSakitViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rumahSakitViewModel =
                ViewModelProviders.of(this).get(RumahSakitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rumah_sakit, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        rumahSakitViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}