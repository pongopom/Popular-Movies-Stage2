package com.example.android.popularmovies.utils;

import android.content.res.Resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by peterpomlett on 18/02/2018.
 * general app utils
 */

public class ApplicationUtils {
    // match activity_detail.xml view size
    private static final int VIEW_WIDTH = 120;

    // This helper method is used return a formatted date string using device local
    // Movie db date format eg = 2018-06-22 use format pattern yyyy-MM-dd

    public static String formattedDateWithLocal(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date;
        try {
            date = format.parse(dateString.replaceAll("Z$", "+0000"));
        } catch (ParseException e) {
            e.printStackTrace();
            //if we have an err just return the string we were given
            return dateString;
        }
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Resources.getSystem().getConfiguration().locale);
        return df.format(date);
    }



}
