package com.example.shawara.popmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.shawara.popmovies.data.MovieContract;
import com.example.shawara.popmovies.model.MoviesDB;

/**
 * Created by shawara on 9/3/2016.
 */

public class Utility {
    public static String getsortType(Context mContext) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sortType = prefs.getString(mContext.getString(R.string.pref_sort_key),
                mContext.getString(R.string.pref_sort_default));
        return sortType;
    }


    public static ContentValues getMovieCV(MoviesDB.Movie movie) {
        ContentValues cv = new ContentValues();

        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        cv.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginal_title());
        cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        cv.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        cv.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPoster_path());
        cv.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVote_average());
        cv.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());

        return cv;
    }
}
