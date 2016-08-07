package com.example.shawara.popmovies;

import android.content.Context;

import com.example.shawara.popmovies.MoviesDB.Movie;

import java.util.List;

/**
 * Created by shawara on 8/1/2016.
 */
public class MoviesLab {
    private static MoviesLab mMoviesLab;
    private Context mContext;
    private MoviesDB mMoviesDB;

    private MoviesLab(Context c){
         mContext=c;
    }

    public static MoviesLab getMoviesLab(Context c){
        if(mMoviesLab==null){
            mMoviesLab=new MoviesLab(c);
        }
        return mMoviesLab;
    }


    public int getPageNo(){
        return mMoviesDB==null?1:mMoviesDB.getPage();
    }

    public List<Movie> getMovies(){
        return (mMoviesDB==null?null:mMoviesDB.getResults());
    }

    public void setmMoviesDB(MoviesDB mMoviesDB) {
        this.mMoviesDB = mMoviesDB;
    }
}
