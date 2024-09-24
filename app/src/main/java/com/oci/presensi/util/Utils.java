package com.oci.presensi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {

    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        Locale id = new Locale("id");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", id);
        return sdf.format(calendar.getTime());
    }

    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        Locale id = new Locale("id");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", id);
        return sdf.format(calendar.getTime());
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        Locale id = new Locale("id");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", id);
        return sdf.format(calendar.getTime());
    }

    public static String getPeriode() {
        Calendar calendar = Calendar.getInstance();
        Locale id = new Locale("id");
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", id);
        return sdf.format(calendar.getTime());
    }
}
