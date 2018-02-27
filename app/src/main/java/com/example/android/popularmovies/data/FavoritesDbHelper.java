package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by peterpomlett on 23/02/2018.
 * SQlite helper
 */

public class FavoritesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoritesTable.db";
    private static final int DATABASE_VERSION = 1;

    FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesContract.FavoritesEntry.TABLE_NAME + " (" +
                FavoritesContract.FavoritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL," +
                FavoritesContract.FavoritesEntry.COLUMN_TITLE + " TEXT," +
                FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE + " TEXT," +
                FavoritesContract.FavoritesEntry.COLUMN_AVERAGE_VOTE + " TEXT," +
                FavoritesContract.FavoritesEntry.COLUMN_PLOT + " TEXT," +
                FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH + " TEXT " +
                "); ";
                     sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoritesContract.FavoritesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
