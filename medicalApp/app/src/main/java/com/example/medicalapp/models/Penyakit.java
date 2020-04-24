package com.example.medicalapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Penyakit implements Parcelable {
    private String IDPenyakit;
    private String namaPenyakit;
    private String ringkasan;
    private String image;
    private boolean header;

    public Penyakit(String IDPenyakit, String namaPenyakit, String ringkasan, String image, boolean header) {
        this.IDPenyakit = IDPenyakit;
        this.namaPenyakit = namaPenyakit;
        this.ringkasan = ringkasan;
        this.image = image;
        this.header = header;
    }

    public Penyakit() {
    }

    protected Penyakit(Parcel in) {
        IDPenyakit = in.readString();
        namaPenyakit = in.readString();
        ringkasan = in.readString();
        image = in.readString();
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

    public String getRingkasan() {
        return ringkasan;
    }

    public void setRingkasan(String ringkasan) {
        this.ringkasan = ringkasan;
    }

    public String getImage() {
        if (image != null){
            return image;
        } else {
            return "no image";
        }
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isHeader() {
        return header;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(IDPenyakit);
        dest.writeString(namaPenyakit);
        dest.writeString(ringkasan);
        dest.writeString(image);
    }
}
