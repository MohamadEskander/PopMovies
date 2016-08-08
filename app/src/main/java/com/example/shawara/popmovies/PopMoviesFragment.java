package com.example.shawara.popmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shawara.popmovies.MoviesDB.Movie;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

/**
 * Created by shawara on 8/1/2016.
 */
public class PopMoviesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private GridLayoutManager glm;


    public static PopMoviesFragment newInstance() {
        return new PopMoviesFragment();
    }


    @Override
    public void onStart() {
        super.onStart();
        updateView(getPageNo());
    }

    private int getPageNo() {
        return MoviesLab.getMoviesLab(getActivity()).getPageNo();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pop_movies, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.pop_movies_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        glm = (GridLayoutManager) mRecyclerView.getLayoutManager();

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean z = true;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int p = getPageNo();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (glm.findFirstCompletelyVisibleItemPosition() == 0) {
                        if (p > 1) updateView(p - 1);
                    } else if (glm.findLastCompletelyVisibleItemPosition() == mRecyclerView.getAdapter().getItemCount() - 1) {
                        if (p < 255) updateView(p + 1);
                    }
                }
            }



        });
        return v;

    }


    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private Movie mMovie;

        public MovieHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_holder_image_view);
            mImageView.setOnClickListener(this);
        }

        public void bindMovieBoster(Movie m) {
            mMovie = m;
            Picasso.with(getActivity()).load(mMovie.getPoster_path()).into(mImageView);

        }

        @Override
        public void onClick(View v) {
            startActivity(MovieDetailActivity.newIntent(getActivity(),mMovie));
        }
    }


    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
        List<Movie> movies;

        public MovieAdapter(List<Movie> m) {
            movies = m;
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.item_movie, parent, false);
            return new MovieHolder(v);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position) {
            holder.bindMovieBoster(movies.get(position));
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }


    public String getsortType() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortType = prefs.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default));
        return sortType;
    }

    private void updateView(int page) {
        List<Movie> m = MoviesLab.getMoviesLab(getActivity()).getMovies();
      MoviesDB md=MoviesLab.getMoviesLab(getActivity()).getmMoviesDB();
        String curSortType=getsortType();
        String listSorttype="";
        if(md!=null)listSorttype=md.getCurrentSortBy();

        int p = getPageNo();
        if (m == null || p != page  || !listSorttype.equals(curSortType) ) {
            new MoviesFetcherTask().execute(page + "");
        } else {
            movieAdapter = new MovieAdapter(m);
            mRecyclerView.setAdapter(movieAdapter);
        }
    }


    private class MoviesFetcherTask extends AsyncTask<String, Void, MoviesDB> {

        @Override
        protected MoviesDB doInBackground(String... params) {
            MoviesDB m = null;
            try {
                m = new HttpFetcher(getActivity(), Integer.parseInt(params[0])).getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return m;
        }


        @Override
        protected void onPostExecute(MoviesDB moviesDB) {
            // Log.d("shawraa", "onPostExecute: "+moviesDB.getResults().size());
            if (moviesDB != null) {
                movieAdapter = new MovieAdapter(moviesDB.getResults());
                mRecyclerView.setAdapter(movieAdapter);

            }
        }
    }
}
