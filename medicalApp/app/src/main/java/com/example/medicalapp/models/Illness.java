package com.example.medicalapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Illness implements Parcelable {
    private String IdPenyakit;
    private String namaPenyakit;
    private String gambarPenyakit;
    private String ringkasan;
    private String penyebab;
    private String gejala;
    private String diagnosa;
    private String pencegahan;
    private String spesialis;
    private boolean header;

    public Illness(String IdPenyakit, String namaPenyakit, String gambarPenyakit, String ringkasan, String penyebab, String gejala, String diagnosa, String pencegahan, String spesialis, boolean header) {
        this.IdPenyakit = IdPenyakit;
        this.namaPenyakit = namaPenyakit;
        this.gambarPenyakit = gambarPenyakit;
        this.ringkasan = ringkasan;
        this.penyebab = penyebab;
        this.gejala = gejala;
        this.diagnosa = diagnosa;
        this.pencegahan = pencegahan;
        this.spesialis = spesialis;
        this.header = header;
    }

    public Illness(String namaPenyakit, boolean header) {
        this.IdPenyakit = "";
        this.namaPenyakit = namaPenyakit;
        this.gambarPenyakit = "";
        this.ringkasan = "";
        this.penyebab = "";
        this.gejala = "";
        this.diagnosa = "";
        this.pencegahan = "";
        this.spesialis = "";
        this.header = header;
    }

    protected Illness(Parcel in) {
        IdPenyakit = in.readString();
        namaPenyakit = in.readString();
        gambarPenyakit = in.readString();
        ringkasan = in.readString();
        penyebab = in.readString();
        gejala = in.readString();
        diagnosa = in.readString();
        pencegahan = in.readString();
        spesialis = in.readString();
    }

    public static final Creator<Illness> CREATOR = new Creator<Illness>() {
        @Override
        public Illness createFromParcel(Parcel in) {
            return new Illness(in);
        }

        @Override
        public Illness[] newArray(int size) {
            return new Illness[size];
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

    public String getGambarPenyakit() {
        if (gambarPenyakit != null){
            return gambarPenyakit;
        } else {
            return "no image";
        }
    }

    public void setGambarPenyakit(String gambarPenyakit) {
        this.gambarPenyakit = gambarPenyakit;
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
        dest.writeString(gambarPenyakit);
        dest.writeString(ringkasan);
        dest.writeString(penyebab);
        dest.writeString(gejala);
        dest.writeString(diagnosa);
        dest.writeString(pencegahan);
        dest.writeString(spesialis);
    }
}
