package com.oci.presensi.model;

import com.google.firebase.firestore.PropertyName;

public class ModelAbsensi {

    int id_absensi, id_user;
    String timestamp, keterangan;

    public ModelAbsensi() {
    }

    public ModelAbsensi(int id_absensi, String timestamp, int id_user, String keterangan) {
        this.id_absensi = id_absensi;
        this.id_user = id_user;
        this.timestamp = timestamp;
        this.keterangan = keterangan;
    }

    @PropertyName("id_absensi")
    public int getId_absensi() {
        return id_absensi;
    }

    @PropertyName("id_absensi")
    public void setId_absensi(int id_absensi) {
        this.id_absensi = id_absensi;
    }

    @PropertyName("id_user")
    public int getId_user() {
        return id_user;
    }

    @PropertyName("id_user")
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @PropertyName("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @PropertyName("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @PropertyName("keterangan")
    public String getKeterangan() {
        return keterangan;
    }

    @PropertyName("keterangan")
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}