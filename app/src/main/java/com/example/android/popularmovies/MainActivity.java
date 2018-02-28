package com.example.android.popularmovies;

import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.data.FavoritesContract;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MoviesAsyncTaskLoader;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ListItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER = 10;
    private ArrayList<Movie> mMovies;
    private MovieRecyclerViewAdapter mMovieRecyclerViewAdapter;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.no_network_textView)
    TextView mNoNetworkTextView;
    @BindView(R.id.movie_recycler_view)
    RecyclerView mMovieRecyclerView;
    @BindString(R.string.movie_detail_Key)
    String mMovieDetailKey;
    @BindString(R.string.recycler_position_key)
    String mRecyclerPositionKey;
    GridLayoutManager mLayoutManager;
    Parcelable listState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //register for pref changes so we can update the movie grid
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        //Set up the recyclerView that will show the movies
        mMovies = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(this, 3);
        mMovieRecyclerView.setLayoutManager(mLayoutManager);
        mMovieRecyclerViewAdapter = new MovieRecyclerViewAdapter(mMovies, this, this);
        mMovieRecyclerView.setAdapter(mMovieRecyclerViewAdapter);
        String fetchMoviesString = sharedPreferences.getString(this.getResources().getString(R.string.fetch_movie_key), this.getResources().getString(R.string.fetch_by_most_popular));
        String favorites = (this.getResources().getString(R.string.fetch_favorites));
        if (fetchMoviesString.equals(favorites)) {
            mMovies = new ArrayList<>();
            getSupportLoaderManager().initLoader(1, null, this);
        } else {
            fetchJsonForMovies();
        }
    }

    //Inflate Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //AsyncTaskLoader for fetch of  movies
    private void fetchJsonForMovies() {
        android.app.LoaderManager loaderManager = getLoaderManager();
        Loader<String> loader = loaderManager.getLoader(MOVIE_LOADER);
        if (loader == null) {
            // If we don't have a loader lets create one
            loaderManager.initLoader(MOVIE_LOADER, null, movieLoaderListener);
        } else {
            // other wise lets restart the loader we have
            loaderManager.restartLoader(MOVIE_LOADER, null, movieLoaderListener);
        }
    }

    public android.app.LoaderManager.LoaderCallbacks<ArrayList<Movie>> movieLoaderListener
            = new android.app.LoaderManager.LoaderCallbacks<ArrayList<Movie>>() {
        @Override
        public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
            mProgressBar.setVisibility(View.VISIBLE);
            mNoNetworkTextView.setVisibility(View.GONE);
            return new MoviesAsyncTaskLoader(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
            if (movies != null) {
                mMovies = movies;
                mMovieRecyclerViewAdapter.setDataSource(mMovies);
                if (listState != null) {
                   mLayoutManager.onRestoreInstanceState(listState);
                }
            } else {
                mNoNetworkTextView.setVisibility(View.VISIBLE);
            }
            mProgressBar.setVisibility(View.GONE);

        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
            mMovies = null;
        }
    };

    //A movie item is tapped
    @Override
    public void onMovieItemClick(Movie movie) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(mMovieDetailKey, movie);
        startActivity(detailIntent);
    }

    //if the prefs have changed refetch movies
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getResources().getString(R.string.fetch_movie_key))) {
            if (s.equals(this.getResources().getString(R.string.fetch_favorites))) {
                mMovies = new ArrayList<>();
                mMovieRecyclerViewAdapter.setDataSource(mMovies);
            } else {
                fetchJsonForMovies();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //clean up unregister pref listener.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    // Using a cursor loader to query on background thread
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri NAME_URI = FavoritesContract.CONTENT_URI;
        return new CursorLoader(this, NAME_URI, null,
                null, null, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader
            , Cursor cursor) {
        if (cursor != null) {
            cursor.moveToPosition(-1);
            try {
                while (cursor.moveToNext()) {
                    String title = cursor.getString(cursor.getColumnIndex
                            (FavoritesContract.FavoritesEntry.COLUMN_TITLE));
                    String movieId = cursor.getString(cursor.getColumnIndex
                            (FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID));
                    String plot = cursor.getString(cursor.getColumnIndex
                            (FavoritesContract.FavoritesEntry.COLUMN_PLOT));
                    String date = cursor.getString(cursor.getColumnIndex
                            (FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE));
                    String vote = cursor.getString(cursor.getColumnIndex
                            (FavoritesContract.FavoritesEntry.COLUMN_AVERAGE_VOTE));
                    String path = cursor.getString(cursor.getColumnIndex
                            (FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH));
                    Movie movie = new Movie(movieId, title, date, vote, plot, path);
                    mMovies.add(movie);
                }
            } finally {
                mMovieRecyclerViewAdapter.setDataSource(mMovies);
                mLayoutManager.onRestoreInstanceState(listState);
            }
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {

    }

    // save recycler scroll position on rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(mRecyclerPositionKey,
                mMovieRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        listState = state.getParcelable(mRecyclerPositionKey);
        System.out.println(listState);
        mMovieRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
    }

}



