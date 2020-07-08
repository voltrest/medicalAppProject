package com.example.medicalapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Article implements Parcelable {
    private String idArtikel;
    private String judul;
    private String gambarArtikel;
    private String badan;
    private String penulis;
    private Date tanggalTulis;
    private Date tanggalCopy;

    public Article(String idArtikel, String judul, String gambarArtikel, String badan, String penulis, Date tanggalTulis, Date tanggalCopy) {
        this.idArtikel = idArtikel;
        this.judul = judul;
        this.gambarArtikel = gambarArtikel;
        this.badan = badan;
        this.penulis = penulis;
        this.tanggalTulis = tanggalTulis;
        this.tanggalCopy = tanggalCopy;
//        this.tanggalTulis = Calendar.getInstance().getTime();
//        this.tanggalCopy = Calendar.getInstance().getTime();
    }

    protected Article(Parcel in) {
        idArtikel = in.readString();
        judul = in.readString();
        gambarArtikel = in.readString();
        badan = in.readString();
        penulis = in.readString();
        tanggalTulis = new Date(in.readLong());
        tanggalCopy = new Date(in.readLong());
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getIdArtikel() {
        return idArtikel;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambarArtikel() {
        return gambarArtikel;
    }

    public void setGambarArtikel(String gambarArtikel) {
        this.gambarArtikel = gambarArtikel;
    }

    public String getBadan() {
        return badan;
    }

    public void setBadan(String badan) {
        this.badan = badan;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public Date getTanggalTulis() {
        return tanggalTulis;
    }

    public void setTanggalTulis(Date tanggalTulis) {
        this.tanggalTulis = tanggalTulis;
    }

    public Date getTanggalCopy() {
        return tanggalCopy;
    }

    public void setTanggalCopy(Date tanggalCopy) {
        this.tanggalCopy = tanggalCopy;
    }

    public static Creator<Article> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idArtikel);
        dest.writeString(judul);
        dest.writeString(gambarArtikel);
        dest.writeString(badan);
        dest.writeString(penulis);
        dest.writeLong(tanggalTulis.getTime());
        dest.writeLong(tanggalCopy.getTime());
    }
}
