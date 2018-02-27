package com.example.android.popularmovies.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

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

    // This helper method is used to calculate the number of rows that can fit on the screen landscape and portrate
    // Information for this found on stackOverflow see link below.
    // https://stackoverflow.com/questions/33575731/gridlayoutmanager-how-to-auto-fit-columns

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / VIEW_WIDTH);
    }

}
