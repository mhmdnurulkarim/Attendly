package com.sofia.presensi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String currentDate = sdf.format(calendar.getTime());
        /*SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = sdf_.format(date);
        String daydate = dayName + ", " + currentDate; */
        return currentDate;
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(calendar.getTime());
        /*SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = sdf_.format(date);
        String daydate = dayName + ", " + currentDate; */
        return currentDate;
    }

    public static String getPeriode(){
        Calendar calendar = Calendar.getInstance();
        Locale id = new Locale("id");
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", id);
        String currentDate = sdf.format(calendar.getTime());
        return currentDate;
    }
}
