package com.oci.presensi.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.oci.presensi.model.ModelAbsensi;
import com.oci.presensi.model.ModelAkun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "presensi.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_AKUN = "create table akun(" +
                "id_user integer primary key, " +
                "username text, " +
                "password text, " +
                "id_role integer, " +
                "nama text, " +
                "nik text, " +
                "divisi text);";
        db.execSQL(CREATE_TABLE_AKUN);

        String CREATE_TABLE_ABSENSI = "create table absensi(" +
                "id_absensi integer primary key, " +
                "timestamp text, " +
                "id_user integer, " +
                "keterangan text);";
        db.execSQL(CREATE_TABLE_ABSENSI);

        // 1 manager
        // 2 admin
        // 3 pegawai

        // --------------- INSERT DATA TO TABLE -----------

        String INSERT_ADAM = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(1, 'adam', '1234', 1, 'Adam', '123456789',  'Manager');";
        db.execSQL(INSERT_ADAM);

        String INSERT_DEA = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(2, 'dea', '1234', 2, 'Dea', '12345672891',  'Admin Koordinator');";
        db.execSQL(INSERT_DEA);

        String INSERT_ABDUL = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(3, 'abdul', '1234', 3, 'Abdul Rosid', '789445612',  'Pekerja Gudang');";
        db.execSQL(INSERT_ABDUL);

        String INSERT_BAKRI = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(4, 'bakri', '1234', 3, 'Bakri', '987456123',  'Pekerja Gudang');";
        db.execSQL(INSERT_BAKRI);

        String INSERT_INAN = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(5, 'inan', '1234', 3, 'Inan', '12345678941',  'Pekerja Gudang');";
        db.execSQL(INSERT_INAN);

        String INSERT_MUHASAN = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(6, 'muhasan', '1234', 3, 'Muhasan', '7894245612',  'Pekerja Gudang');";
        db.execSQL(INSERT_MUHASAN);

        String INSERT_TUMIN = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(7, 'tumin', '1234', 3, 'Tumin', '9872456123',  'Pekerja Gudang');";
        db.execSQL(INSERT_TUMIN);

        String INSERT_SAMSUL = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(8, 'samsul', '1234', 3, 'Samsul', '7894455612',  'Pekerja Gudang');";
        db.execSQL(INSERT_SAMSUL);

        String INSERT_ASEP = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(9, 'asep', '1234', 3, 'Asep', '98764561223',  'Pekerja Gudang');";
        db.execSQL(INSERT_ASEP);

        String INSERT_USMAN = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(10, 'usman', '1234', 3, 'Usman', '1234567891',  'Pekerja Gudang');";
        db.execSQL(INSERT_USMAN);

        String INSERT_ULYAH = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(11, 'ulyah', '1234', 3, 'Ulyah', '7894456512',  'Pekerja Gudang');";
        db.execSQL(INSERT_ULYAH);

        String INSERT_AINUN = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(12, 'ainun', '1234', 3, 'Ainun', '1987456123',  'Pekerja Gudang');";
        db.execSQL(INSERT_AINUN);

        String INSERT_EKA = "INSERT INTO akun" +
                "(id_user, username, password, id_role, nama, nik, divisi) VALUES " +
                "(13, 'eka', '1234', 3, 'Eka', '7894425612',  'Pekerja Gudang');";
        db.execSQL(INSERT_EKA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS absensi");
        onCreate(db);
    }


    // ---------------- AKUN --------------

    public List<ModelAkun> getAllAkun() {
        List<ModelAkun> listModelAkun = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM akun", null);

        if (cursor.moveToFirst()) {
            do {
                ModelAkun akun = new ModelAkun(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                listModelAkun.add(akun);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listModelAkun;
    }

    public ModelAkun getAkun(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM akun WHERE id_user = ?", new String[]{String.valueOf(id)});

        ModelAkun akun = null;
        if (cursor.moveToFirst()) {
            akun = new ModelAkun(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
        }

        cursor.close();
        db.close();
        return akun;
    }

    public List<ModelAkun> getAllPegawai(int id_tipe_akun) {
        List<ModelAkun> listModelAkun = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM akun WHERE id_role = ?", new String[]{String.valueOf(id_tipe_akun)});

        if (cursor.moveToFirst()) {
            do {
                ModelAkun akun = new ModelAkun(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                listModelAkun.add(akun);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listModelAkun;
    }

    public String getNamaUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nama = null;
        Cursor cursor = db.rawQuery("SELECT nama FROM akun WHERE id_user = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            nama = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return nama;
    }

    // ---------------- ABSENSI --------------

    public List<ModelAbsensi> getAllAbsensi() {
        List<ModelAbsensi> listModelAbsensi = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_absensi, timestamp, id_user, keterangan FROM absensi", null);

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

        cursor.close();
        db.close();
        return listModelAbsensi;
    }

    public void addNewAbsensi(int id, String timestamp, int idAkun, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO absensi(id_absensi, timestamp, id_user, keterangan) VALUES (?, ?, ?, ?)",
                new Object[]{id, timestamp, idAkun, keterangan});
        db.close();
    }

    public int getLastIdAbsensi() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(id_absensi) FROM absensi", null);
        int lastID = 0;

        if (cursor.moveToFirst()) {
            lastID = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return lastID + 1;
    }

    public List<ModelAbsensi> getAllAbsensiByIdUser(int id) {
        List<ModelAbsensi> listModelAbsensi = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_absensi, timestamp, id_user, keterangan FROM absensi WHERE id_user = ?", new String[]{String.valueOf(id)});

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

        cursor.close();
        db.close();
        return listModelAbsensi;
    }

    // ---------------- FIRESTORE --------------

    public void saveAttendance(int id, String timestamp, int userId, String keterangan) {
        addNewAbsensi(id, timestamp, userId, keterangan);
        saveAttendanceToFirestore(id, timestamp, userId, keterangan);
    }

    public void saveAttendanceToFirestore(int id, String timestamp, int userId, String keterangan) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> attendance = new HashMap<>();
        attendance.put("id_absensi", id);
        attendance.put("timestamp", timestamp);
        attendance.put("id_user", userId);
        attendance.put("keterangan", keterangan);

        db.collection("absensi")
                .document(String.valueOf(userId))
                .collection("absensiUser")
                .document(String.valueOf(id))
                .set(attendance)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));
    }

    public void getAttendanceFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collectionGroup("absensiUser")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
                        sqLiteDatabase.beginTransaction();

                        try {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelAbsensi attendance = document.toObject(ModelAbsensi.class);

                                ContentValues contentValues = new ContentValues();
                                contentValues.put("id_absensi", attendance.getId_absensi());
                                contentValues.put("timestamp", attendance.getTimestamp());
                                contentValues.put("id_user", attendance.getId_user());
                                contentValues.put("keterangan", attendance.getKeterangan());

                                int rowsUpdated = sqLiteDatabase.update("absensi", contentValues, "id_absensi = ?", new String[]{String.valueOf(attendance.getId_absensi())});

                                if (rowsUpdated == 0) {
                                    sqLiteDatabase.insert("absensi", null, contentValues);
                                }
                            }
                            sqLiteDatabase.setTransactionSuccessful();
                        } catch (Exception e) {
                            Log.w("SQLite", "Error processing attendance data.", e);
                        } finally {
                            sqLiteDatabase.endTransaction();
                            sqLiteDatabase.close();
                        }
                    } else {
                        Log.w("Firestore", "Error getting attendance documents.", task.getException());
                    }
                });
    }


    public void fetchAttendanceData(int userId, FetchAttendanceCallback callback) {
        List<ModelAbsensi> localAbsensi = getAllAbsensiByIdUser(userId);
        List<ModelAbsensi> combinedAbsensi = new ArrayList<>(localAbsensi);

        FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
        dbFirestore.collection("absensi")
                .document(String.valueOf(userId))
                .collection("absensiUser")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ModelAbsensi absensi = new ModelAbsensi(
                                    document.getLong("id_absensi").intValue(),
                                    document.getString("timestamp"),
                                    document.getLong("id_user").intValue(),
                                    document.getString("keterangan")
                            );

                            boolean existsInLocal = false;
                            for (ModelAbsensi local : localAbsensi) {
                                if (local.getId_absensi() == absensi.getId_absensi()) {
                                    existsInLocal = true;
                                    break;
                                }
                            }

                            if (!existsInLocal) {
                                addNewAbsensi(absensi.getId_absensi(), absensi.getTimestamp(), absensi.getId_user(), absensi.getKeterangan());
                                combinedAbsensi.add(absensi);
                            }
                        }
                        callback.onFetchComplete(combinedAbsensi);
                    } else {
                        callback.onFetchFailed(task.getException());
                    }
                });
    }

    public interface FetchAttendanceCallback {
        void onFetchComplete(List<ModelAbsensi> attendanceList);

        void onFetchFailed(Exception e);
    }
}