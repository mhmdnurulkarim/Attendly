package com.sofia.presensi.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.sofia.presensi.model.ModelAbsensi;
import com.sofia.presensi.model.ModelAkun;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {

    private  static final String DATABASE_NAME = "presensi.db";
    private static final int DATABASE_VERSION = 2;

    public DataHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_AKUN = "create table akun(" +
                "id_user integer primary key autoincrement, " +
                "username text, " +
                "password text, " +
                "id_role integer, " +
                "nama text, " +
                "nik integer, " +
                "divisi text);";
        db.execSQL(CREATE_TABLE_AKUN);

        String CREATE_TABLE_ABSENSI = "create table absensi(" +
                "id_absensi integer primary key autoincrement, " +
                "timestamp text, " +
                "id_user integer, " +
                "keterangan text);";
        db.execSQL(CREATE_TABLE_ABSENSI);

        // 1 manager
        // 2 admin
        // 3 pegawai

        // --------------- INSERT DATA TO TABLE -----------

//        String INSERT_INTO_AKUN = "INSERT INTO akun" +
//                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
//                "(1, 'manager', '1234', 1, 'Adam', 123456789,  'Manager');";
//        db.execSQL(INSERT_INTO_AKUN);

        String INSERT_INTO_AKUN2 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(2, 'usman', '1234', 3, 'Usman', 1234567891,  'Pekerja Gudang');";
        db.execSQL(INSERT_INTO_AKUN2);

        String INSERT_INTO_AKUN3 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(3, 'abdul', '1234', 3, 'Abdul Rosid', 789445612,  'Pekerja Gudang');";
        db.execSQL(INSERT_INTO_AKUN3);

        String INSERT_INTO_AKUN4 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(4, 'bakri', '1234', 3, 'Bakri', 987456123,  'Pekerja Gudang');";
        db.execSQL(INSERT_INTO_AKUN4);

        String INSERT_INTO_AKUN5 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(5, 'inan', '1234', 3, 'Inan', 12345678941,  'Pekerja Gudang');";
        db.execSQL(INSERT_INTO_AKUN5);

        String INSERT_INTO_AKUN6 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(6, 'muhasan', '1234', 3, 'Muhasan', 7894245612,  'Pekerja Gudang');";
        db.execSQL(INSERT_INTO_AKUN6);

        String INSERT_INTO_AKUN7 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(7, 'tumin', '1234', 3, 'Tumin', 9872456123,  'Pekerja Gudang');";
        db.execSQL(INSERT_INTO_AKUN7);

        String INSERT_INTO_AKUN8 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(8, 'samsul', '1234', 3, 'Samsul', 7894455612,  'Pekerja Gudang');";
        db.execSQL(INSERT_INTO_AKUN8);

        String INSERT_INTO_AKUN9 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(9, 'asep', '1234', 3, 'Asep', 98764561223,  'Pekerja Gudang');";
        db.execSQL(INSERT_INTO_AKUN9);

        String INSERT_INTO_AKUN10 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(10, 'dea', '1234', 2, 'Dea', 12345672891,  'Admin Koordinator');";
        db.execSQL(INSERT_INTO_AKUN10);

        String INSERT_INTO_AKUN11 = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(11, 'ulyah', '1234', 3, 'Ulyah', 7894456512,  'Pekerja Gudang');";
        db.execSQL(INSERT_INTO_AKUN11);

//        String INSERT_INTO_AKUN12 = "INSERT INTO akun" +
//                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
//                "(12, 'ainun', '1234', 3, 'Ainun', 1987456123,  'Pekerja Gudang');";
//        db.execSQL(INSERT_INTO_AKUN12);
//
//        String INSERT_INTO_AKUN13 = "INSERT INTO akun" +
//                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
//                "(13, 'eka', '1234', 3, 'Eka', 7894425612,  'Pekerja Gudang');";
//        db.execSQL(INSERT_INTO_AKUN13);
//
//        String INSERT_INTO_AKUN14 = "INSERT INTO akun" +
//                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
//                "(14, 'dea', '1234', 3, 'Dea', 9874561234,  'Pekerja Gudang');";
//        db.execSQL(INSERT_INTO_AKUN14);


    }


    // ---------------- AKUN --------------

    public List<ModelAkun> getAllAkun() {
        List<ModelAkun> listModelAkun = new ArrayList<ModelAkun>();
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM akun", null);
        cursor.moveToFirst();
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelAkun akun = new ModelAkun(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6));
                listModelAkun.add(akun);
            } while (cursor.moveToNext());
        }
        return listModelAkun;
    }

    public ModelAkun getAkun(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM akun WHERE id_user = '" + id + "'", null);
        String[] user = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            user[cc] = cursor.getString(0).toString();
        }
        ModelAkun akun = new ModelAkun(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getString(6));
        return akun;
    }

    public List<ModelAkun> getAllPegawai(int id_tipe_akun) {
        List<ModelAkun> listModelAkun = new ArrayList<ModelAkun>();
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM akun WHERE id_role = '" + id_tipe_akun + "'",null);
        cursor.moveToFirst();
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelAkun akun = new ModelAkun(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6));
                listModelAkun.add(akun);
            } while (cursor.moveToNext());
        }
        return listModelAkun;
    }

    public String getNamaUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM akun WHERE id_user = '" + id + "'", null);
        String[] user = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            user[cc] = cursor.getString(0).toString();
        }
        ModelAkun akun = new ModelAkun(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getString(6));
        return akun.getNama();
    }

    /*public List<ModelAkun> getAllAkunByIdAkun(int idAkun) {
        List<ModelAkun> listModelAkun = new ArrayList<ModelAkun>();
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM akun WHERE id_akun = '" + idAkun + "'", null);
        cursor.moveToFirst();
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelAkun akun = new ModelAkun(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(9),
                        cursor.getString(10),
                        cursor.getInt(11),
                        cursor.getString(12),
                        cursor.getString(13));
                listModelAkun.add(akun);
            } while (cursor.moveToNext());
        }
        return listModelAkun;
    }

    public ModelAkun getAkun(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM akun WHERE id = '" + id + "'", null);
        String[] user = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            user[cc] = cursor.getString(0).toString();
        }
        ModelAkun akun = new ModelAkun(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getInt(9),
                cursor.getString(10),
                cursor.getInt(11),
                cursor.getString(12),
                cursor.getString(13));
        return akun;
    }*/


    // ---------------- ABSENSI --------------

    public List<ModelAbsensi> getAllAbsensi() {
        List<ModelAbsensi> listModelAbsensi = new ArrayList<ModelAbsensi>();
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM absensi", null);
        cursor.moveToFirst();
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelAbsensi absensi = new ModelAbsensi(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3));
                listModelAbsensi.add(absensi);
            } while (cursor.moveToNext());
        }
        return listModelAbsensi;
    }

    public void addNewAbsensi(int id, String timestamp, int idAkun, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into absensi(id_absensi, timestamp, id_user, keterangan) VALUES ('" +
                id + "','" +
                timestamp + "','" +
                idAkun + "','" +
                keterangan + "')");
        db.close();
    }

    public int getLastIdAbsensi(){
        List<ModelAbsensi> produkList = getAllAbsensi();
        int lastID = produkList.size();
        return lastID+1;
    }

    public List<ModelAbsensi> getAllAbsensiByIdUser(int id) {
        List<ModelAbsensi> listModelAbsensi = new ArrayList<ModelAbsensi>();
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM absensi where id_user = '" + id + "'", null);
        cursor.moveToFirst();
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelAbsensi absensi = new ModelAbsensi(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3));
                listModelAbsensi.add(absensi);
            } while (cursor.moveToNext());
        }
        return listModelAbsensi;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}