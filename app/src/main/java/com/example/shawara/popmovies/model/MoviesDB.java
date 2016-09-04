package com.example.shawara.popmovies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shawara on 8/1/2016.
 */
public class MoviesDB {

    /**
     * page : 2
     * results : []
     * total_results : 5098
     * total_pages : 255
     */

    private int page;
    private String currentSortBy;


    /**
     * poster_path : /aAmfIX3TT40zUHGcCKrlOZRKC7u.jpg
     * adult : false
     * overview : Growing up can be a bumpy road, and it's no exception for Riley, who is uprooted from her Midwest life when her father starts a new job in San Francisco. Like all of us, Riley is guided by her emotions - Joy, Fear, Anger, Disgust and Sadness. The emotions live in Headquarters, the control center inside Riley's mind, where they help advise her through everyday life. As Riley and her emotions struggle to adjust to a new life in San Francisco, turmoil ensues in Headquarters. Although Joy, Riley's main and most important emotion, tries to keep things positive, the emotions conflict on how best to navigate a new city, house and school.
     * release_date : 2015-06-09
     * genre_ids : [35,16,10751]
     * id : 150540
     * original_title : Inside Out
     * original_language : en
     * title : Inside Out
     * backdrop_path : /szytSpLAyBh3ULei3x663mAv5ZT.jpg
     * popularity : 5.592569
     * vote_count : 3321
     * video : false
     * vote_average : 8.02
     */

    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCurrentSortBy() {
        return currentSortBy;
    }

    public void setCurrentSortBy(String currentSortBy) {
        this.currentSortBy = currentSortBy;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public static class Movie implements Serializable {

        private String id;
        private long _id;
        private String poster_path;
        private String overview;
        private String release_date;
        @SerializedName("title")
        private String original_title;
        private double vote_average;
        private double popularity;

        public String getPoster_path() {
            return poster_path;
        }

        public long get_id() {
            return _id;
        }

        public void set_id(long _id) {
            this._id = _id;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date.substring(0, 4);
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getVote_average() {
            return vote_average + "/10";
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }
    }
}
