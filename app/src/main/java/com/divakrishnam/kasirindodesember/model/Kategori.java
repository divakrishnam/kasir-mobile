package com.divakrishnam.kasirindodesember.model;

public class Kategori {
    private String kategoriId;
    private String kategoriNama;

    public Kategori() {
    }

    public Kategori(String kategoriId, String kategoriNama) {
        this.kategoriId = kategoriId;
        this.kategoriNama = kategoriNama;
    }

    public String getKategoriId() {
        return kategoriId;
    }

    public String getKategoriNama() {
        return kategoriNama;
    }

    public void setKategoriId(String kategoriId) {
        this.kategoriId = kategoriId;
    }

    public void setKategoriNama(String kategoriNama) {
        this.kategoriNama = kategoriNama;
    }
}
