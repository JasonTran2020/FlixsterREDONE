package com.example.flixsterredone.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String backDropPath;
    String title;
    String posterPath;
    String overview;
    int movieId;
    double rating;

    //Empty constructor for Parcel
    public Movie(){}

    //Notice how we don't have try/catch even though we are using keys that may not exist?
    //Because we are throwing a JSONException we are essentially that whoever uses this class needs to handle the JSONException

    public Movie(JSONObject jsonObject) throws JSONException {
        backDropPath=jsonObject.getString("backdrop_path");
        posterPath=jsonObject.getString("poster_path");
        title=jsonObject.getString("title");
        overview=jsonObject.getString("overview");
        rating= jsonObject.getDouble("vote_average");
        movieId=jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies=new ArrayList<>();
        for(int i=0; i<movieJsonArray.length();i++)
        {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getTitle() {
        return title;
    }

    //The posterPath attribute actually only has the last part of the link, so we need to attach the base url to it to get
    //a full link to the image
    //YEA WE'RE HARDCODING IT TO w342.
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackDropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backDropPath);
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieId() {
        return movieId;
    }
}
