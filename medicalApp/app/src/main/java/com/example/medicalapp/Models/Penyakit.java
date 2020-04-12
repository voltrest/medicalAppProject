package com.example.medicalapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Penyakit implements Parcelable {
    private String IDPenyakit;
    private String namaPenyakit;
    private String penjelasanPenyakit;

    public Penyakit(String IDPenyakit, String namaPenyakit, String penjelasanPenyakit) {
        this.IDPenyakit = IDPenyakit;
        this.namaPenyakit = namaPenyakit;
        this.penjelasanPenyakit = penjelasanPenyakit;
    }

    public Penyakit() {
    }

    protected Penyakit(Parcel in) {
        IDPenyakit = in.readString();
        namaPenyakit = in.readString();
        penjelasanPenyakit = in.readString();
    }

    public static final Creator<Penyakit> CREATOR = new Creator<Penyakit>() {
        @Override
        public Penyakit createFromParcel(Parcel in) {
            return new Penyakit(in);
        }

        @Override
        public Penyakit[] newArray(int size) {
            return new Penyakit[size];
        }
    };

    public String getIDPenyakit() {
        return IDPenyakit;
    }

    public String getNamaPenyakit() {
        return namaPenyakit;
    }

    public void setNamaPenyakit(String namaPenyakit) {
        this.namaPenyakit = namaPenyakit;
    }

    public String getPenjelasanPenyakit() {
        return penjelasanPenyakit;
    }

    public void setPenjelasanPenyakit(String penjelasanPenyakit) {
        this.penjelasanPenyakit = penjelasanPenyakit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(IDPenyakit);
        dest.writeString(namaPenyakit);
        dest.writeString(penjelasanPenyakit);
    }
}
