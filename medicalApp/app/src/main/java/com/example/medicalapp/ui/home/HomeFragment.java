package com.example.medicalapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.medicalapp.R;
import com.example.medicalapp.ui.pandemi.KasusPerProvinsiActivity;
import com.example.medicalapp.ui.pandemi.PandemiActivity;

public class HomeFragment extends Fragment {
    private Button mButton;

    private HomeViewModel homeViewModel;

    private Activity mActivity;
    ListView listView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mActivity =(Activity) context;
        }
        Log.i("tag", "onAttach completed");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        Log.i("tag", "onDetach completed");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mButton = root.findViewById(R.id.button_pandemi);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PandemiActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}