package com.oci.presensi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {

    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String currentDate = sdf.format(calendar.getTime());
        return currentDate;
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(calendar.getTime());
        return currentDate;
    }

    public static String getPeriode() {
        Calendar calendar = Calendar.getInstance();
        Locale id = new Locale("id");
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", id);
        String currentDate = sdf.format(calendar.getTime());
        return currentDate;
    }
}
