package com.divakrishnam.kasirindodesember.model;

public class Barang {
    private String barangId;
    private String barangNama;
    private String barangKategori;
    private String barangStok;
    private String barangHarga;

    public Barang() {
    }

    public Barang(String barangId, String barangNama, String barangKategori, String barangStok, String barangHarga) {
        this.barangId = barangId;
        this.barangNama = barangNama;
        this.barangKategori = barangKategori;
        this.barangStok = barangStok;
        this.barangHarga = barangHarga;
    }

    public String getBarangId() {
        return barangId;
    }

    public String getBarangNama() {
        return barangNama;
    }

    public String getBarangKategori() {
        return barangKategori;
    }

    public String getBarangStok() {
        return barangStok;
    }

    public String getBarangHarga() {
        return barangHarga;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public void setBarangNama(String barangNama) {
        this.barangNama = barangNama;
    }

    public void setBarangKategori(String barangKategori) {
        this.barangKategori = barangKategori;
    }

    public void setBarangStok(String barangStok) {
        this.barangStok = barangStok;
    }

    public void setBarangHarga(String barangHarga) {
        this.barangHarga = barangHarga;
    }
}
