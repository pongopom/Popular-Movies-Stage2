package com.example.android.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.data.Review;

import java.util.ArrayList;

/**
 * Created by peterpomlett on 20/02/2018.
 * for showing reviews in detail view
 */

public class ReviewRecyclerViewAdapter extends
        RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {

    private ArrayList<Review> mDataSource;


    void setDataSource(ArrayList<Review> dataSource) {
        mDataSource = dataSource;
        notifyDataSetChanged();
    }

    ReviewRecyclerViewAdapter(ArrayList<Review> dataSource) {
        this.mDataSource = dataSource;
    }

    @Override
    public ReviewRecyclerViewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_view_holder, parent, false);
        return new ReviewRecyclerViewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewRecyclerViewAdapter.ReviewViewHolder holder, int position) {
        Review review = mDataSource.get(position);
        String author = review.getAuthor();
        String content = review.getContent();
        holder.mAuthorTextView.setText(author);
        holder.mContentTextView.setText(content);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthorTextView;
        TextView mContentTextView;

        ReviewViewHolder(View itemView) {
            super(itemView);
            mAuthorTextView = itemView.findViewById(R.id.review_author_tv);
            mContentTextView = itemView.findViewById(R.id.review_content_tv);
        }
    }
}
