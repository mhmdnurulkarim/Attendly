package com.oci.presensi.model;

import com.google.firebase.firestore.PropertyName;

public class ModelAkun {

    private int id_user, id_role;
    private String username, password, nama, nik, divisi;

    public ModelAkun() {
    }

    public ModelAkun(int id_user, String username, String password, int id_role, String nama, String nik, String divisi) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.id_role = id_role;
        this.nama = nama;
        this.nik = nik;
        this.divisi = divisi;
    }

    @PropertyName("id_user")
    public int getIdUser() {
        return id_user;
    }

    @PropertyName("id_user")
    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    @PropertyName("username")
    public String getUsername() {
        return username;
    }

    @PropertyName("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @PropertyName("password")
    public String getPassword() {
        return password;
    }

    @PropertyName("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @PropertyName("id_role")
    public int getId_role() {
        return id_role;
    }

    @PropertyName("id_role")
    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    @PropertyName("nama")
    public String getNama() {
        return nama;
    }

    @PropertyName("nama")
    public void setNama(String nama) {
        this.nama = nama;
    }

    @PropertyName("nik")
    public String getNik() {
        return nik;
    }

    @PropertyName("nik")
    public void setNik(String nik) {
        this.nik = nik;
    }

    @PropertyName("divisi")
    public String getDivisi() {
        return divisi;
    }

    @PropertyName("divisi")
    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }
}
