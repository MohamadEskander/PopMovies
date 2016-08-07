package com.example.shawara.popmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shawara.popmovies.MoviesDB.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by shawara on 8/1/2016.
 */
public class MovieDetailFragment extends Fragment {
    public static final String EXTRA_MOVIE = "com.example.shawara.popmovies.MovieDetailFragment.movie";
    private TextView mTitleTextView;
    private TextView mDateTextView;
    private TextView mRateTextView;
    private TextView mOverviewTextView;
    private ImageView mMovieImageView;
    private Button mMakeFavoriteButton;
    private Movie movie;


    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        movie = (Movie) getActivity().getIntent().getSerializableExtra(EXTRA_MOVIE);

        mTitleTextView = (TextView) v.findViewById(R.id.fragment_detail_movie_title);
        mDateTextView = (TextView) v.findViewById(R.id.fragment_detail_movie_year);
        mRateTextView = (TextView) v.findViewById(R.id.fragment_detail_movie_rate);
        mOverviewTextView = (TextView) v.findViewById(R.id.fragment_detail_movie_overview);
        mMovieImageView=(ImageView)v.findViewById(R.id.fragment_detail_movie_image);
        mMakeFavoriteButton=(Button)v.findViewById(R.id.fragment_detail_movie_make_favorite);

        mTitleTextView.setText(movie.getOriginal_title());
        mRateTextView.setText(movie.getVote_average());
        mOverviewTextView.setText(movie.getOverview());
        mDateTextView.setText(movie.getRelease_date());
        Picasso.with(getActivity()).load(movie.getPoster_path()).into(mMovieImageView);
        return v;
    }
}
