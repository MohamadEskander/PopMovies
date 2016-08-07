package com.example.shawara.popmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MovieDetailActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return MovieDetailFragment.newInstance();
    }

    public static Intent newIntent(Context c, MoviesDB.Movie movie){
        Intent i=new Intent(c,MovieDetailActivity.class);
        i.putExtra(MovieDetailFragment.EXTRA_MOVIE,movie);
        return i;
    }
}
