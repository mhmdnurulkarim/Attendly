package com.oci.presensi.model;

public class ModelAkun {

    int id_user, id_role, nik;
    String username, password, nama, divisi;

    public ModelAkun(int id_user, String username, String password, int id_role, String nama, int nik, String divisi) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.id_role = id_role;
        this.nama = nama;
        this.nik = nik;
        this.divisi = divisi;
    }

    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int id) {
        this.id_user = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNik() {
        return nik;
    }

    public void setNik(int nik) {
        this.nik = nik;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }
}
