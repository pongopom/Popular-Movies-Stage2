package com.example.android.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.popularmovies.data.Trailer;

import java.util.ArrayList;

/**
 * Created by peterpomlett on 21/02/2018.
 * list for trailers
 */

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<com.example.android.popularmovies.TrailerRecyclerViewAdapter.TrailerViewHolder> {

    private ArrayList<Trailer> mDataSource;
    private final ButtonTapped mButtonTapped;

    TrailerRecyclerViewAdapter(ArrayList<Trailer> dataSource, ButtonTapped buttonTapped) {
        mDataSource = dataSource;
        mButtonTapped = buttonTapped;
    }

    void setDataSource(ArrayList<Trailer> dataSource) {
        mDataSource = dataSource;
        notifyDataSetChanged();
    }


    public interface ButtonTapped{
        void trailerSelected(String videoKey) ;
    }


    @Override
    public TrailerRecyclerViewAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_view_holder, parent, false);
        return new TrailerRecyclerViewAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerRecyclerViewAdapter.TrailerViewHolder holder, int position) {
        Trailer trailer = mDataSource.get(position);
        String name = trailer.getName();
        holder.mTrailerButton.setText(name);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button mTrailerButton;

        TrailerViewHolder(View itemView) {
            super(itemView);
            mTrailerButton = itemView.findViewById(R.id.trailer_button);
            mTrailerButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
         Trailer trailer = mDataSource.get(position);
            mButtonTapped.trailerSelected(trailer.getVideoKey());
        }
    }

}
