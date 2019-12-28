package com.divakrishnam.kasirindodesember.model;

public class Transaksi {
    private int transaksiId;
    private String transaksiKasir;
    private String transaksiWaktu;

    public Transaksi() {
    }

    public Transaksi(int transaksiId, String transaksiKasir, String transaksiWaktu) {
        this.transaksiId = transaksiId;
        this.transaksiKasir = transaksiKasir;
        this.transaksiWaktu = transaksiWaktu;
    }

    public int getTransaksiId() {
        return transaksiId;
    }

    public String getTransaksiKasir() {
        return transaksiKasir;
    }

    public String getTransaksiWaktu() {
        return transaksiWaktu;
    }

    public void setTransaksiId(int transaksiId) {
        this.transaksiId = transaksiId;
    }

    public void setTransaksiKasir(String transaksiKasir) {
        this.transaksiKasir = transaksiKasir;
    }

    public void setTransaksiWaktu(String transaksiWaktu) {
        this.transaksiWaktu = transaksiWaktu;
    }
}
