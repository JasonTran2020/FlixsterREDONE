package com.example.flixsterredone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixsterredone.DetailsActivity;
import com.example.flixsterredone.R;
import com.example.flixsterredone.databinding.ItemMovieBinding;
import com.example.flixsterredone.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolderMovie>{


    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @NotNull
    @Override
    //Inflates a layout and returns it as a ViewHolderMovie
    public ViewHolderMovie onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //Removed due to databinding
        //View movieView=LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        ItemMovieBinding binding=ItemMovieBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolderMovie(binding);
    }

    @Override
    //Recieves a ViewHolderMovie and binds teh corresponding data to the correct views of the ViewHolderMovie
    public void onBindViewHolder(@NonNull @NotNull ViewHolderMovie holder, int position) {
        Movie movie=movies.get(position);
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolderMovie extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivPoster;
        ConstraintLayout container;
        ItemMovieBinding binding;

        public ViewHolderMovie(@NonNull @NotNull ItemMovieBinding binding) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(binding.getRoot());

            //Was
            /*tvTitle=itemView.findViewById(R.id.tvTitle);
            tvDescription=itemView.findViewById(R.id.tvDescription);
            ivPoster=itemView.findViewById(R.id.ivPoster);
            container=itemView.findViewById(R.id.movie);*/

            //But with data binding
            this.binding= binding;
            tvTitle=binding.tvTitle;
            tvDescription=binding.tvDescription;
            ivPoster=binding.ivPoster;
            container=binding.getRoot();
            //Technically this could also be removed and replaced (in the bind method) with
            //binding.tvTitle.setText(title);
            //....


        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvDescription.setText(movie.getOverview());

            //Check orientation and then determine which image to use depending on orientation
            if (context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            {
                Glide.with(context).load(movie.getBackDropPath()).placeholder(R.drawable.flicks_movie_placeholder).transform(new RoundedCorners(100)).into(ivPoster);
            }
            else{
                Glide.with(context).load(movie.getPosterPath()).placeholder(R.drawable.flicks_movie_placeholder).transform(new RoundedCorners(100)).into(ivPoster);
            }
            //When clicking on any part of the ViewHolder, go to the details activity for that movie.
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i= new Intent(context, DetailsActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);

                }
            });

        }
    }
}
