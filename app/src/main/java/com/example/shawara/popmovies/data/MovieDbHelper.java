package com.example.shawara.popmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shawara.popmovies.data.MovieContract.FavoriteEntry;
import com.example.shawara.popmovies.data.MovieContract.MovieEntry;
import com.example.shawara.popmovies.data.MovieContract.PopularEntry;
import com.example.shawara.popmovies.data.MovieContract.TopRatedEntry;

/**
 * Created by shawara on 9/1/2016.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW + " TEXT , " +
                MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL UNIQUE ON CONFLICT REPLACE, " +
                MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL " +
                " );";

        final String SQL_CREATE_POPULAR_TABLE = "CREATE TABLE " + PopularEntry.TABLE_NAME + " (" +
                PopularEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PopularEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL UNIQUE ON CONFLICT REPLACE, " +
                " FOREIGN KEY (" + PopularEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + ") " +

                " );";

        final String SQL_CREATE_TOP_RATED_TABLE = "CREATE TABLE " + TopRatedEntry.TABLE_NAME + " (" +
                TopRatedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TopRatedEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL UNIQUE ON CONFLICT REPLACE, " +
                " FOREIGN KEY (" + TopRatedEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + ") " +

                " );";


        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL UNIQUE ON CONFLICT REPLACE, " +
                " FOREIGN KEY (" + FavoriteEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + ") " +

                " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_POPULAR_TABLE);
        db.execSQL(SQL_CREATE_TOP_RATED_TABLE);
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PopularEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TopRatedEntry.TABLE_NAME);
        onCreate(db);

    }
}
