package com.example.flixsterredone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixsterredone.Adapters.MovieAdapter;
import com.example.flixsterredone.databinding.ActivityMainBinding;
import com.example.flixsterredone.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    //The API key is embedded within this URL which is bad practice but this keep has literally been passed around
    //thousands of people already so
    public static final String TAG="MainActivity";
    public static final String NOW_PLAYING_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    List<Movie> movies;
    RecyclerView rvMovies;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());

        View view=binding.getRoot();
        setContentView(view);

        movies=new ArrayList<>();
        //Was rvMovies=findViewById(R.id.rvMovies); but with data binding
        rvMovies=binding.rvMovies;
        AsyncHttpClient client= new AsyncHttpClient();
        //Create adapter
        MovieAdapter adapter=new MovieAdapter(this,movies);
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        //Set adapter to the RecylerView
        //Movie API is gonna return a Json response which is why we are choosing to a Json handler
        //Other handlers exists for other responses
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,"Json Recieved");
                JSONObject jsonObject=json.jsonObject;

                //Json requires a try/catch because the key "results" may not exist
                //We know it exist because we checked the response, but the program doesn't know that
                try {
                    JSONArray results=jsonObject.getJSONArray("results");
                    Log.i(TAG,"Results "+results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"Json failed to retrieve");
            }
        });

    }
}