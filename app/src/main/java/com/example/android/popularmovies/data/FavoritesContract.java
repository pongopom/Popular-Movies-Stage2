package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by peterpomlett on 23/02/2018.
 * Contract class dB/ContentProvider queries
 */

public class FavoritesContract {

    public static final class FavoritesEntry implements BaseColumns {

        static final String TABLE_NAME = "favoritesTable";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_TITLE = "movieTitle";
        public static final String COLUMN_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_AVERAGE_VOTE = "movieAverageVote";
        public static final String COLUMN_PLOT = "moviePlot";
        public static final String COLUMN_POSTER_PATH = "moviePosterPath";


    }

    // URI's used by contentProvider
    static final String AUTHORITY = "com.example.android.popularmovies";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    //below is what we use in our queries
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(FavoritesEntry.TABLE_NAME).build();




}
