package com.divakrishnam.kasirindodesember.model;

public class DetailTransaksi {
    private int detailTransaksiId;
    private String detailTransaksiBarang;
    private String detailTransaksiIdBarang;
    private int detailTransaksiJumlahBarang;
    private String detailTransaksiTotalHarga;

    public DetailTransaksi() {
    }

    public DetailTransaksi(int detailTransaksiId, String detailTransaksiBarang, String detailTransaksiIdBarang, int detailTransaksiJumlahBarang, String detailTransaksiTotalHarga) {
        this.detailTransaksiId = detailTransaksiId;
        this.detailTransaksiBarang = detailTransaksiBarang;
        this.detailTransaksiIdBarang = detailTransaksiIdBarang;
        this.detailTransaksiJumlahBarang = detailTransaksiJumlahBarang;
        this.detailTransaksiTotalHarga = detailTransaksiTotalHarga;
    }

    public int getDetailTransaksiId() {
        return detailTransaksiId;
    }

    public void setDetailTransaksiId(int detailTransaksiId) {
        this.detailTransaksiId = detailTransaksiId;
    }

    public String getDetailTransaksiBarang() {
        return detailTransaksiBarang;
    }

    public void setDetailTransaksiBarang(String detailTransaksiBarang) {
        this.detailTransaksiBarang = detailTransaksiBarang;
    }

    public String getDetailTransaksiIdBarang() {
        return detailTransaksiIdBarang;
    }

    public void setDetailTransaksiIdBarang(String detailTransaksiIdBarang) {
        this.detailTransaksiIdBarang = detailTransaksiIdBarang;
    }

    public int getDetailTransaksiJumlahBarang() {
        return detailTransaksiJumlahBarang;
    }

    public void setDetailTransaksiJumlahBarang(int detailTransaksiJumlahBarang) {
        this.detailTransaksiJumlahBarang = detailTransaksiJumlahBarang;
    }

    public String getDetailTransaksiTotalHarga() {
        return detailTransaksiTotalHarga;
    }

    public void setDetailTransaksiTotalHarga(String detailTransaksiTotalHarga) {
        this.detailTransaksiTotalHarga = detailTransaksiTotalHarga;
    }
}
