package com.example.medicalapp.ui.illnessList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DaftarPenyakitViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DaftarPenyakitViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}