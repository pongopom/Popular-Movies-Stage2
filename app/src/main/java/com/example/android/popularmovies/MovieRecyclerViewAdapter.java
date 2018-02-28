package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Created by peterpomlett on 16/02/2018.
 * To show movie thumb nails on main activity
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private ArrayList<Movie> mDataSource;
    private final ListItemClickListener mListItemClickListener;
    private Context mContext;

    void setDataSource(ArrayList<Movie> dataSource) {
        mDataSource = dataSource;
        notifyDataSetChanged();
    }

    MovieRecyclerViewAdapter(ArrayList<Movie> dataSource, Context context, ListItemClickListener listItemClickListener) {
        this.mDataSource = dataSource;
        this.mContext = context;
        this.mListItemClickListener = listItemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_view_holder, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie details = mDataSource.get(position);
        String posterPath = details.getPosterPath();
        NetworkUtils.loadMoviePosterIntoImageView(mContext, posterPath, holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }


    public interface ListItemClickListener {
        void onMovieItemClick(Movie movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mMoviePoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = itemView.findViewById(R.id.poster_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListItemClickListener.onMovieItemClick(mDataSource.get(position));
        }
    }





}
