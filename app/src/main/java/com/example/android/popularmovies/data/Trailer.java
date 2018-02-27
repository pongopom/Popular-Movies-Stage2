package com.example.android.popularmovies.data;

/**
 * Created by peterpomlett on 20/02/2018.
 * Trailer object
 */

public class Trailer {

    private String mName;
    private String mVideoKey;

    public Trailer(String name, String videoKey) {
        mName = name;
        mVideoKey = videoKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getVideoKey() {
        return mVideoKey;
    }

}
