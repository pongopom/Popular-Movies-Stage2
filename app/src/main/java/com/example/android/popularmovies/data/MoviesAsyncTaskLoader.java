package com.example.android.popularmovies.data;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by peterpomlett on 16/02/2018.
 * for fetching movies to populate MovieRecyclerViewadapter
 */

public class MoviesAsyncTaskLoader extends AsyncTaskLoader <ArrayList<Movie>> {

    public MoviesAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        Context context = this.getContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String fetchMoviesString = sharedPreferences.getString(context.getResources().getString(R.string.fetch_movie_key), context.getResources().getString(R.string.fetch_by_most_popular));
        // do the backGround work on another thread
        URL url = NetworkUtils.buildUrlForMovies(null, fetchMoviesString);
        String result = "";
        try {
           result = NetworkUtils.getResponseFromHttpUrl(url);//This just create a HTTPUrlConnection and return result in strings
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  NetworkUtils.moviesFromJson(result, context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}






