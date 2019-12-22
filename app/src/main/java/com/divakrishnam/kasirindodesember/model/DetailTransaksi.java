package com.divakrishnam.kasirindodesember.model;

public class DetailTransaksi {
    private String detailTransaksiId;
    private String detailTransaksiBarang;
    private String detailTransaksiJumlahBarang;
    private String detailTransaksiTotalHarga;

    public DetailTransaksi() {
    }

    public DetailTransaksi(String detailTransaksiId, String detailTransaksiBarang, String detailTransaksiJumlahBarang, String detailTransaksiTotalHarga) {
        this.detailTransaksiId = detailTransaksiId;
        this.detailTransaksiBarang = detailTransaksiBarang;
        this.detailTransaksiJumlahBarang = detailTransaksiJumlahBarang;
        this.detailTransaksiTotalHarga = detailTransaksiTotalHarga;
    }

    public String getDetailTransaksiId() {
        return detailTransaksiId;
    }

    public String getDetailTransaksiBarang() {
        return detailTransaksiBarang;
    }

    public String getDetailTransaksiJumlahBarang() {
        return detailTransaksiJumlahBarang;
    }

    public String getDetailTransaksiTotalHarga() {
        return detailTransaksiTotalHarga;
    }

    public void setDetailTransaksiId(String detailTransaksiId) {
        this.detailTransaksiId = detailTransaksiId;
    }

    public void setDetailTransaksiBarang(String detailTransaksiBarang) {
        this.detailTransaksiBarang = detailTransaksiBarang;
    }

    public void setDetailTransaksiJumlahBarang(String detailTransaksiJumlahBarang) {
        this.detailTransaksiJumlahBarang = detailTransaksiJumlahBarang;
    }

    public void setDetailTransaksiTotalHarga(String detailTransaksiTotalHarga) {
        this.detailTransaksiTotalHarga = detailTransaksiTotalHarga;
    }
}
