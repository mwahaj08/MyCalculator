package com.concertpharmaceutical.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tkxel on 7/10/2015.
 */
public class DateUtil {

    public static final String FORMAT_TIME_ONLY = "h:mm a";
    public static final String FORMAT_SHIPMENT_LIST = "EEE, MMM, d";
    public static final String FORMAT_SHIPMENT_DETAIL = "MMM d, yyyy";
    public static final String FORMATE_DATE_WITH_DASHES = "yyyy-MM-dd";
    public static final String FORMATE_DATE_WITH_DASHES_2 = "dd-MMM-yyyy";
    public static final String INPUT_FORMAT_DATE_TIME = "yyyy-MM-dd hh:mm:ss";

    private static String TAG = "DateUtil";


    public static Date parseDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return null;
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return date.toString();
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis() - 2000;
    }

}
