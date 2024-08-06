package com.sofia.presensi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class PreferenceUtils extends AppCompatActivity {

    public PreferenceUtils() {
    }

    public static boolean saveIdAkun(int id, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(Constants.KEY_ID_AKUN, id);
        prefsEditor.apply();
        return true;
    }

    public static int getIdAkun(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(Constants.KEY_ID_AKUN, 0);
    }


    public static boolean saveNama(String nama, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_NAMA, nama);
        prefsEditor.apply();
        return true;
    }

    public static String getNama(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_NAMA, "");
    }

    public static boolean saveDivisi(String divisi, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_DIVISI, divisi);
        prefsEditor.apply();
        return true;
    }

    public static String getDivisi(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_DIVISI, "");
    }

    public static boolean saveIdRole(int id, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(Constants.KEY_ID_ROLE, id);
        prefsEditor.apply();
        return true;
    }

    public static int getIdRole(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(Constants.KEY_ID_ROLE, 0);
    }

    public static boolean saveNik(int nik, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(Constants.KEY_NIK, nik);
        prefsEditor.apply();
        return true;
    }

    public static int getNik(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(Constants.KEY_NIK, 0);
    }



}
