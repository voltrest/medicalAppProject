package com.example.medicalapp.models;

public class Myth {
    private String IdMyth;
    private String pertanyaan;
    private String jawaban;

    public Myth(String idMyth, String pertanyaan, String jawaban) {
        IdMyth = idMyth;
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;
    }

    public String getIdMyth() {
        return IdMyth;
    }

    public void setIdMyth(String idMyth) {
        IdMyth = idMyth;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
}
