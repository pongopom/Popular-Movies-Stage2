package com.example.android.popularmovies.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Trailer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by peterpomlett on 16/02/2018.
 * Network helper utility class
 */

public class NetworkUtils {

    //For api key follow link --->  https://www.themoviedb.org/documentation/api
    //TODO get API key from THE MOVIE DB and set String API_KEY below to = "YOUR KEY". So replace BuildConfig.API_KEY with your key
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String SCHEME = "https";
    private static final String AUTHORITY = "api.themoviedb.org";
    private static final String PATH_1 = "3";
    private static final String PATH_2 = "movie";
    private static final String KEY_QUERY = "api_key";
    private static final String POSTER_PATH = "https://image.tmdb.org/t/p/w185%s";
    private static final String URI_APP = "vnd.youtube:";
    private static final String URI_WEB = "http://www.youtube.com/watch?v=";

    //Used to parse in our url and hopefully get some Json back
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildUrlForMovies(String id, String searchType) {
        Uri.Builder urlBuilder = new Uri.Builder();
        urlBuilder.scheme(SCHEME);
        urlBuilder.authority(AUTHORITY);
        urlBuilder.appendPath(PATH_1);
        urlBuilder.appendPath(PATH_2);
        if (id != null) {
            urlBuilder.appendPath(id);
        }
        urlBuilder.appendPath(searchType);
        urlBuilder.appendQueryParameter(KEY_QUERY, API_KEY);
        URL url = null;
        try {
            url = new URL(urlBuilder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //Used to return an arrayList of movie details
    public static ArrayList<Movie> moviesFromJson(String jsonString, Context context) {
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray results = jsonObject.getJSONArray(context.getResources().getString(R.string.json_results_array));
                ArrayList<Movie> movieList = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject r = results.getJSONObject(i);
                    String id = r.getString("id");
                    String title = r.getString(context.getResources().getString(R.string.title));
                    String releaseDate = r.getString(context.getResources().getString(R.string.releaseDate));
                    String averageVote = r.getString(context.getResources().getString(R.string.averageVote));
                    String plot = r.getString(context.getResources().getString(R.string.plot));
                    String posterPath = r.getString(context.getResources().getString(R.string.posterPath));
                    Movie movie = new Movie(id, title, releaseDate, averageVote, plot, posterPath);
                    movieList.add(movie);
                }
                return movieList;
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //Used to return an arrayList of reviews
    public static ArrayList<Review> reviewsForMovie(String jsonString, Context context) {
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray results = jsonObject.getJSONArray(context.getResources().getString(R.string.json_results_array));
                ArrayList<Review> reviewList = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject r = results.getJSONObject(i);
                    String author = r.getString("author");
                    String content = r.getString("content");
                    Review review = new Review(author, content);
                    reviewList.add(review);
                }
                return reviewList;
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<Trailer> trailersForMovie(String jsonString, Context context) {
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray results = jsonObject.getJSONArray(context.getResources().getString(R.string.json_results_array));
                ArrayList<Trailer> trailerList = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject r = results.getJSONObject(i);
                    String name = r.getString("name");
                    String videoKey = r.getString("key");
                    Trailer trailer = new Trailer(name, videoKey);
                    trailerList.add(trailer);
                }
                return trailerList;
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //Used to fetch movie posters using Picasso
    public static void loadMoviePosterIntoImageView(Context context, String path, ImageView view) {
        Picasso.with(context).load(String.format(POSTER_PATH, path)).into(view);
    }

    //used to start trailer intent
    public static void getIntentForTrailer(Context context, String videoKey) {
        Intent youTubeApp = new Intent(Intent.ACTION_VIEW, Uri.parse(URI_APP + videoKey));
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(URI_WEB + videoKey));
        try {
            context.startActivity(youTubeApp);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(browser);
        }
    }


    public static String linkForTrailer(String videoKey){
       return  URI_WEB + videoKey;
    }

}
