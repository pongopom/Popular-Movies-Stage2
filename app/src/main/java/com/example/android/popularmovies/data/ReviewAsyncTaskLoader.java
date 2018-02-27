package com.example.android.popularmovies.data;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import com.example.android.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by peterpomlett on 19/02/2018.
 * for fetching reviews
 */

public class ReviewAsyncTaskLoader extends AsyncTaskLoader <ArrayList<Review>>{

    private Bundle mBundle;
    public ReviewAsyncTaskLoader(Context context, Bundle bundle) {
        super(context);
        mBundle = bundle;
    }

    @Override
    public ArrayList<Review> loadInBackground() {
        Context context = this.getContext();
        String movieId = mBundle.getString("movie_id");
        String result = "";
        URL url = NetworkUtils.buildUrlForMovies(movieId, "reviews");
        try {
            result = NetworkUtils.getResponseFromHttpUrl(url);//This just create a HTTPUrlConnection and return result in strings
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NetworkUtils.reviewsForMovie(result, context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
