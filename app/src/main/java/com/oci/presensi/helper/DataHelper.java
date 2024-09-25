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
        createTableAkun(db);
        createTableAbsensi(db);
    }

    private void createTableAkun(SQLiteDatabase db) {
        String CREATE_TABLE_AKUN = "CREATE TABLE akun (" +
                "id_user INTEGER PRIMARY KEY, " +
                "username TEXT, " +
                "password TEXT, " +
                "id_role INTEGER, " +
                "nama TEXT, " +
                "nik TEXT, " +
                "divisi TEXT)";
        db.execSQL(CREATE_TABLE_AKUN);
    }

    private void createTableAbsensi(SQLiteDatabase db) {
        String CREATE_TABLE_ABSENSI = "CREATE TABLE absensi (" +
                "id_absensi INTEGER PRIMARY KEY, " +
                "timestamp TEXT, " +
                "id_user INTEGER, " +
                "keterangan TEXT)";
        db.execSQL(CREATE_TABLE_ABSENSI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS akun");
        db.execSQL("DROP TABLE IF EXISTS absensi");
        onCreate(db);
    }

    // ---------------- AKUN --------------
    public void addNewAkun(ModelAkun akun) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO akun (id_user, username, password, id_role, nama, nik, divisi) VALUES (?, ?, ?, ?, ?, ?, ?)",
                new Object[]{akun.getIdUser(), akun.getUsername(), akun.getPassword(), akun.getId_role(), akun.getNama(), akun.getNik(), akun.getDivisi()});

        FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> employee = new HashMap<>();
        employee.put("id_user", akun.getIdUser());
        employee.put("username", akun.getUsername());
        employee.put("password", akun.getPassword());
        employee.put("id_role", akun.getId_role());
        employee.put("nama", akun.getNama());
        employee.put("nik", akun.getNik());
        employee.put("divisi", akun.getDivisi());

        dbFirestore.collection("akun")
                .document(String.valueOf(akun.getIdUser()))
                .set(employee)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Employee added successfully"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error adding employee", e));
    }

    public void updateAkun(ModelAkun akun) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE akun SET username = ?, password = ?, id_role = ?, nama = ?, nik = ?, divisi = ? WHERE id_user = ?",
                new Object[]{akun.getUsername(), akun.getPassword(), akun.getId_role(), akun.getNama(), akun.getNik(), akun.getDivisi(), akun.getIdUser()});

        FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> employee = new HashMap<>();
        employee.put("username", akun.getUsername());
        employee.put("password", akun.getPassword());
        employee.put("id_role", akun.getId_role());
        employee.put("nama", akun.getNama());
        employee.put("nik", akun.getNik());
        employee.put("divisi", akun.getDivisi());

        dbFirestore.collection("akun")
                .document(String.valueOf(akun.getIdUser()))
                .update(employee)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Employee updated successfully"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error updating employee", e));
    }

    public void deleteAkun(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM akun WHERE id_user = ?", new String[]{String.valueOf(id)});

        FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
        dbFirestore.collection("akun")
                .document(String.valueOf(id))
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Employee deleted successfully"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error deleting employee", e));
    }

    public void getAkunFromFirebase(OnDataLoadedCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("akun")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ModelAkun akun = document.toObject(ModelAkun.class);

                            Log.d("Firestore", "id_user: " + akun.getIdUser());

                            ContentValues values = new ContentValues();
                            values.put("id_user", akun.getIdUser());
                            values.put("username", akun.getUsername());
                            values.put("password", akun.getPassword());
                            values.put("id_role", akun.getId_role());
                            values.put("nama", akun.getNama());
                            values.put("nik", akun.getNik());
                            values.put("divisi", akun.getDivisi());

                            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM akun WHERE id_user = ?",
                                    new String[]{String.valueOf(akun.getIdUser())});

                            if (cursor != null && cursor.moveToFirst()) {
                                sqLiteDatabase.update("akun", values, "id_user = ?", new String[]{String.valueOf(akun.getIdUser())});
                            } else {
                                sqLiteDatabase.insert("akun", null, values);
                            }

                            if (cursor != null) {
                                cursor.close();
                            }
                        }

                        callback.onDataLoaded();
                    } else {
                        Log.w("Firestore", "Error getting akun documents.", task.getException());
                    }
                });
    }

    public interface OnDataLoadedCallback {
        void onDataLoaded();
    }

    public List<ModelAkun> getAllAkun() {
        List<ModelAkun> listModelAkun = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
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
        return listModelAkun;
    }

    public ModelAkun getAkun(int id) {
        SQLiteDatabase db = getReadableDatabase();
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
        return akun;
    }

    public List<ModelAkun> getAllPegawai(int id_tipe_akun) {
        List<ModelAkun> listModelAkun = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
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
        return listModelAkun;
    }

    public String getNamaUser(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String nama = null;
        Cursor cursor = db.rawQuery("SELECT nama FROM akun WHERE id_user = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            nama = cursor.getString(0);
        }
         cursor.close();
        return nama;
    }

    public int getLastIdAkun() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(id_user) FROM akun", null);
        int lastID = 0;

        if (cursor.moveToFirst()) {
            lastID = cursor.getInt(0);
        }
         cursor.close();
        return lastID + 1;
    }

    // ---------------- ABSENSI --------------
    public void addNewAbsensi(int id_absensi, String timestamp, int id_user, String keterangan) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO absensi(id_absensi, timestamp, id_user, keterangan) VALUES (?, ?, ?, ?)",
                new Object[]{id_absensi, timestamp, id_user, keterangan});

        FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> attendance = new HashMap<>();
        attendance.put("id_absensi", id_absensi);
        attendance.put("timestamp", timestamp);
        attendance.put("id_user", id_user);
        attendance.put("keterangan", keterangan);

        dbFirestore.collection("absensi")
                .document(String.valueOf(id_user))
                .collection("absensiUser")
                .document(String.valueOf(id_absensi))
                .set(attendance)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));
    }

    public List<ModelAbsensi> getAllAbsensi() {
        List<ModelAbsensi> listModelAbsensi = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
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
        return listModelAbsensi;
    }

    public int getLastIdAbsensi() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(id_absensi) FROM absensi", null);
        int lastID = 0;

        if (cursor.moveToFirst()) {
            lastID = cursor.getInt(0);
        }
         cursor.close();
        return lastID + 1;
    }

    public List<ModelAbsensi> getAllAbsensiByIdUser(int id) {
        List<ModelAbsensi> listModelAbsensi = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
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
        return listModelAbsensi;
    }

    public void getAttendanceFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collectionGroup("absensiUser")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ModelAbsensi attendance = document.toObject(ModelAbsensi.class);

                            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM absensi WHERE id_absensi = ?", new String[]{String.valueOf(attendance.getId_absensi())});

                            if (cursor != null && cursor.moveToFirst()) {
                                sqLiteDatabase.execSQL("UPDATE absensi SET " +
                                        "timestamp = '" + attendance.getTimestamp() + "', " +
                                        "id_user = '" + attendance.getId_user() + "', " +
                                        "keterangan = '" + attendance.getKeterangan() + "' " +
                                        "WHERE id_absensi = '" + attendance.getId_absensi() + "'");
                            } else {
                                sqLiteDatabase.execSQL("INSERT INTO absensi (id_absensi, timestamp, id_user, keterangan) VALUES ('" +
                                        attendance.getId_absensi() + "','" +
                                        attendance.getTimestamp() + "','" +
                                        attendance.getId_user() + "','" +
                                        attendance.getKeterangan() + "')");
                            }

                            if (cursor != null) {
                                 cursor.close();
                            }
                        }

                         sqLiteDatabase.close();
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