package com.example.medicalapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Penyakit implements Parcelable {
    private String IdPenyakit;
    private String namaPenyakit;
    private String image;
    private String ringkasan;
    private String penyebab;
    private String gejala;
    private String diagnosa;
    private String pencegahan;
    private String spesialis;
    private boolean header;

    public Penyakit(String IdPenyakit, String namaPenyakit, String image, String ringkasan, String penyebab, String gejala, String diagnosa, String pencegahan, String spesialis, boolean header) {
        this.IdPenyakit = IdPenyakit;
        this.namaPenyakit = namaPenyakit;
        this.image = image;
        this.ringkasan = ringkasan;
        this.penyebab = penyebab;
        this.gejala = gejala;
        this.diagnosa = diagnosa;
        this.pencegahan = pencegahan;
        this.spesialis = spesialis;
        this.header = header;
    }

    public Penyakit(String namaPenyakit, boolean header) {
        this.IdPenyakit = "";
        this.namaPenyakit = namaPenyakit;
        this.image = "";
        this.ringkasan = "";
        this.penyebab = "";
        this.gejala = "";
        this.diagnosa = "";
        this.pencegahan = "";
        this.spesialis = "";
        this.header = header;
    }

    protected Penyakit(Parcel in) {
        IdPenyakit = in.readString();
        namaPenyakit = in.readString();
        image = in.readString();
        ringkasan = in.readString();
        penyebab = in.readString();
        gejala = in.readString();
        diagnosa = in.readString();
        pencegahan = in.readString();
        spesialis = in.readString();
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

    public String getIdPenyakit() {
        return IdPenyakit;
    }

    public String getNamaPenyakit() {
        return namaPenyakit;
    }

    public void setNamaPenyakit(String namaPenyakit) {
        this.namaPenyakit = namaPenyakit;
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

    public String getRingkasan() {
        return ringkasan;
    }

    public void setRingkasan(String ringkasan) {
        this.ringkasan = ringkasan;
    }

    public String getPenyebab() {
        return penyebab;
    }

    public void setPenyebab(String penyebab) {
        this.penyebab = penyebab;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getPencegahan() {
        return pencegahan;
    }

    public void setPencegahan(String pencegahan) {
        this.pencegahan = pencegahan;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
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
        dest.writeString(IdPenyakit);
        dest.writeString(namaPenyakit);
        dest.writeString(image);
        dest.writeString(ringkasan);
        dest.writeString(penyebab);
        dest.writeString(gejala);
        dest.writeString(diagnosa);
        dest.writeString(pencegahan);
        dest.writeString(spesialis);
    }
}
