package com.sofia.presensi.model;

public class ModelAbsensi {

    int id_absensi, id_user;
    String timestamp, keterangan;

    public ModelAbsensi(int id_absensi, String timestamp, int id_user, String keterangan) {
        this.id_absensi = id_absensi;
        this.id_user = id_user;
        this.timestamp = timestamp;
        this.keterangan = keterangan;
    }

    public int getId_absensi() {
        return id_absensi;
    }

    public void setId_absensi(int id_absensi) {
        this.id_absensi = id_absensi;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
