package com.divakrishnam.kasirindodesember.model;

public class Transaksi {
    private String transaksiId;
    private String transaksiKasir;
    private String transaksiWaktu;
    private String transaksiTotalBelanja;

    public Transaksi() {
    }

    public Transaksi(String transaksiId, String transaksiKasir, String transaksiWaktu, String transaksiTotalBelanja) {
        this.transaksiId = transaksiId;
        this.transaksiKasir = transaksiKasir;
        this.transaksiWaktu = transaksiWaktu;
        this.transaksiTotalBelanja = transaksiTotalBelanja;
    }

    public String getTransaksiId() {
        return transaksiId;
    }

    public String getTransaksiKasir() {
        return transaksiKasir;
    }

    public String getTransaksiWaktu() {
        return transaksiWaktu;
    }

    public String getTransaksiTotalBelanja() {
        return transaksiTotalBelanja;
    }

    public void setTransaksiId(String transaksiId) {
        this.transaksiId = transaksiId;
    }

    public void setTransaksiKasir(String transaksiKasir) {
        this.transaksiKasir = transaksiKasir;
    }

    public void setTransaksiWaktu(String transaksiWaktu) {
        this.transaksiWaktu = transaksiWaktu;
    }

    public void setTransaksiTotalBelanja(String transaksiTotalBelanja) {
        this.transaksiTotalBelanja = transaksiTotalBelanja;
    }
}
