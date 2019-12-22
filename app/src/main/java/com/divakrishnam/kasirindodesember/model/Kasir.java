package com.divakrishnam.kasirindodesember.model;

public class Kasir {
    private String kasirId;
    private String kasirNama;
    private String kasirUsername;
    private String kasirPassword;

    public Kasir() {
    }

    public Kasir(String kasirId, String kasirNama, String kasirUsername, String kasirPassword) {
        this.kasirId = kasirId;
        this.kasirNama = kasirNama;
        this.kasirUsername = kasirUsername;
        this.kasirPassword = kasirPassword;
    }

    public String getKasirId() {
        return kasirId;
    }

    public String getKasirNama() {
        return kasirNama;
    }

    public String getKasirUsername() {
        return kasirUsername;
    }

    public String getKasirPassword() {
        return kasirPassword;
    }

    public void setKasirId(String kasirId) {
        this.kasirId = kasirId;
    }

    public void setKasirNama(String kasirNama) {
        this.kasirNama = kasirNama;
    }

    public void setKasirUsername(String kasirUsername) {
        this.kasirUsername = kasirUsername;
    }

    public void setKasirPassword(String kasirPassword) {
        this.kasirPassword = kasirPassword;
    }
}
