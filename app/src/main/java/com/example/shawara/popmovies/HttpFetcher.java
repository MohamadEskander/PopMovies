package com.example.shawara.popmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * Created by shawara on 7/22/2016.
 */
public class HttpFetcher {
    private Context mContext;

    private static final String TAG = "HttpFetcher";
    private static final String PAGE_PARAM = "page";
    private static final String APIKEY_PARAM = "api_key";
    private static final String units = "metric";
    private static int mPage = 1;


    //q=94043&mode=json&units=metric&cnt=7";
    private static final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/";


    public HttpFetcher(Context context, int page) {
        mContext = context;
        mPage = page;
    }


    private String buildUrl(String sort_by) {
        String url = Uri.parse(FORECAST_BASE_URL + sort_by).buildUpon()
                .appendQueryParameter(PAGE_PARAM, Integer.toString(mPage))
                .appendQueryParameter(APIKEY_PARAM, BuildConfig.POP_MOVIES_API_KEY)
                .build().toString();
        //   Log.d(TAG,url);
        return url;
    }


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }


            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }


    public MoviesDB getData() throws IOException {
        String url = buildUrl(getsortType());
        String data = new String(getUrlBytes(url));
        // Log.d(TAG, data);
        if (data != null && data.length() > 0) {
            Gson gson = new GsonBuilder().create();
            MoviesDB moviesDB = gson.fromJson(data, MoviesDB.class);
            //  return moviesDB;
            MoviesLab.getMoviesLab(mContext).setmMoviesDB(moviesDB);
            return moviesDB;
        }
        return null;
    }

    public String getsortType() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sortType = prefs.getString(mContext.getString(R.string.pref_sort_key),
                mContext.getString(R.string.pref_sort_default));
        return sortType;
    }


}
