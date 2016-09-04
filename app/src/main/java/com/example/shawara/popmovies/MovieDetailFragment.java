package com.example.shawara.popmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shawara.popmovies.data.MovieContract;
import com.example.shawara.popmovies.data.MovieContract.FavoriteEntry;
import com.example.shawara.popmovies.model.MoviesDB.Movie;
import com.example.shawara.popmovies.model.Reviews;
import com.example.shawara.popmovies.model.Reviews.Review;
import com.example.shawara.popmovies.model.Trailers;
import com.example.shawara.popmovies.model.Trailers.Trailer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by shawara on 8/1/2016.
 */
public class MovieDetailFragment extends Fragment {
    public static final String EXTRA_MOVIE = "MovieDetailFragment.movie";
    //  private RecyclerView mReviewRecyclerView;
    private RecyclerView mTrailerRecyclerView;
    //  private ReviewsAdapter mReviewAdapter;
    private TrailersAdapter mTrailerAdapter;
    private LinearLayout mLinearLayout;
    private List<Trailer> mTrailerList = new ArrayList<>();
    private List<Review> mReviewList = new ArrayList<>();

    private TextView mTitleTextView;
    private TextView mDateTextView;
    private TextView mRateTextView;
    private TextView mOverviewTextView;
    private ImageView mMovieImageView;
    private ImageView mMakeFavoriteStar;
    private Movie movie;
    private final static String LOG_TAG = "MovieDetailFragment";
    private boolean mFavorite;
    private final static int FAVORITE_LOADER = 1;
    private String mFirstTrailer;
    private ShareActionProvider mShareActionProvider;

    public static MovieDetailFragment newInstance(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MOVIE, movie);
        MovieDetailFragment mdf = new MovieDetailFragment();
        mdf.setArguments(bundle);
        return mdf;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        setHasOptionsMenu(true);

        if (getArguments() == null) return v;
        Object obj = getArguments().getSerializable(EXTRA_MOVIE);
        if (obj != null)
            movie = (Movie) obj;

        mLinearLayout = (LinearLayout) v.findViewById(R.id.reviews_liner_layout);
//        mReviewRecyclerView = (RecyclerView) v.findViewById(R.id.reviews_recycler_view);
//        mReviewRecyclerView.setNestedScrollingEnabled(false);
//        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mReviewAdapter = new ReviewsAdapter(mReviewList);
//        mReviewRecyclerView.setAdapter(mReviewAdapter);

        mTrailerRecyclerView = (RecyclerView) v.findViewById(R.id.trailers_recycler_view);
        mTrailerRecyclerView.setNestedScrollingEnabled(false);

        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTrailerAdapter = new TrailersAdapter(mTrailerList);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);


        mTitleTextView = (TextView) v.findViewById(R.id.fragment_detail_movie_title);
        mDateTextView = (TextView) v.findViewById(R.id.fragment_detail_movie_year);
        mRateTextView = (TextView) v.findViewById(R.id.fragment_detail_movie_rate);
        mOverviewTextView = (TextView) v.findViewById(R.id.fragment_detail_movie_overview);
        mMovieImageView = (ImageView) v.findViewById(R.id.fragment_detail_movie_image);
        mMakeFavoriteStar = (ImageView) v.findViewById(R.id.fragment_detail_movie_make_favorite);


        mMakeFavoriteStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFavorite) {
                    getActivity().getContentResolver().delete(FavoriteEntry.CONTENT_URI, FavoriteEntry.COLUMN_MOVIE_ID + " = ? ",
                            new String[]{movie.getId() + ""});
                    mMakeFavoriteStar.setImageResource(R.drawable.rate_star_big_off_holo_light);
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(FavoriteEntry.COLUMN_MOVIE_ID, movie.getId());
                    getActivity().getContentResolver().insert(FavoriteEntry.CONTENT_URI, cv);
                    ;
                    mMakeFavoriteStar.setImageResource(R.drawable.rate_star_big_on_holo_light);
                }
                mFavorite = !mFavorite;
            }
        });


        mTitleTextView.setText(movie.getOriginal_title());
        mRateTextView.setText(movie.getVote_average());
        mOverviewTextView.setText(movie.getOverview());
        mDateTextView.setText(movie.getRelease_date());
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + movie.getPoster_path()).into(mMovieImageView);


        new TrailersTask().execute(movie.getId());
        new ReviewsTask().execute(movie.getId());


        return v;
    }


    private void checkFavoriteStar() {
        Cursor c = getActivity().getContentResolver().query(FavoriteEntry.CONTENT_URI,
                null,
                FavoriteEntry.TABLE_NAME + "." + FavoriteEntry.COLUMN_MOVIE_ID + " = ? ",
                new String[]{movie.getId() + ""}, null);

        mFavorite = c.moveToFirst();
        c.close();
        if (mFavorite) mMakeFavoriteStar.setImageResource(R.drawable.rate_star_big_on_holo_light);
        else mMakeFavoriteStar.setImageResource(R.drawable.rate_star_big_off_holo_light);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (movie != null) {
            checkFavoriteStar();
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.fragment_detail, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (mFirstTrailer != null) {
            mShareActionProvider.setShareIntent(createShareTrailerIntent());
        }
    }

    private Intent createShareTrailerIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mFirstTrailer);
        return shareIntent;
    }

    private class TrailersTask extends AsyncTask<String, Void, List<Trailer>> {

        @Override
        protected List<Trailer> doInBackground(String... params) {
            try {
                return new HttpFetcher(getContext(), 1).getTrailers(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            if (trailers != null) {
                if (trailers.size() > 0) {
                    mFirstTrailer = "http://www.youtube.com/watch?v=" + trailers.get(0).getKey();
                    if (mShareActionProvider != null)
                        mShareActionProvider.setShareIntent(createShareTrailerIntent());
                }
                mTrailerAdapter.setList(trailers);
                mTrailerAdapter.notifyDataSetChanged();
            }
        }
    }


    private class ReviewsTask extends AsyncTask<String, Void, List<Review>> {

        @Override
        protected List<Review> doInBackground(String... params) {
            try {
                return new HttpFetcher(getContext(), 1).getReviews(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            if (reviews != null) {
                mLinearLayout.removeAllViews();

                for (int i = 0; i < reviews.size(); i++) {
                    View item = getActivity().getLayoutInflater().inflate(R.layout.review_list_item, mLinearLayout, false);
                    ((TextView) (item.findViewById(R.id.list_item_review_tv))).setText(reviews.get(i).getContent());
                    ((TextView) (item.findViewById(R.id.reviewer_name))).setText(reviews.get(i).getAuthor() + " : ");
                    mLinearLayout.addView(item);
                }

//                mReviewAdapter.setList(reviews);
//                mReviewAdapter.notifyDataSetChanged();
                mReviewList = reviews;
            }
        }
    }

/*
    private class ReviewsAdapter extends RecyclerView.Adapter<ReviewHolder> {
        List<Review> mList;

        public ReviewsAdapter(List<Review> m) {
            mList = m;
        }

        public void setList(List<Review> mList) {
            this.mList = mList;
        }

        @Override
        public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.review_list_item, parent, false);
            return new ReviewHolder(v);
        }

        @Override
        public void onBindViewHolder(ReviewHolder holder, int position) {
            holder.bindData(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
*/

    private class TrailersAdapter extends RecyclerView.Adapter<TrailerHolder> {
        List<Trailer> mList;

        public void setList(List<Trailer> mList) {
            this.mList = mList;
        }

        public TrailersAdapter(List<Trailer> m) {
            mList = m;
        }

        @Override
        public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.trailer_list_item, parent, false);
            return new TrailerHolder(v);
        }

        @Override
        public void onBindViewHolder(TrailerHolder holder, int position) {
            holder.bindData(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

/*
    private class ReviewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView review;
        private Review mReview;

        public ReviewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.reviewer_name);
            review = (TextView) itemView.findViewById(R.id.list_item_review_tv);
        }

        public void bindData(Review r) {
            mReview = r;
            name.setText(r.getAuthor());
            review.setText(r.getContent());
        }


    }*/

    private class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private Trailer mTrailer;

        public TrailerHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.trailer_name);
            itemView.setOnClickListener(this);
        }

        public void bindData(Trailer t) {
            mTrailer = t;
            name.setText(t.getName());
        }

        @Override
        public void onClick(View v) {

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + mTrailer.getKey()));
            if (i.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(i);
            } else {
                Log.d(LOG_TAG, "Couldn't play video, no receiving apps installed!");
            }

        }
    }

}
