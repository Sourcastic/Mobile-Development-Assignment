package com.example.l230759ass1;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public interface OnBookClickListener {
        void onBookClick(Movie movie);
    }

    private final ArrayList<Movie> movies;
    private final boolean showBookButton;
    private final OnBookClickListener bookClickListener;

    public MovieAdapter(ArrayList<Movie> movies, boolean showBookButton,
                        OnBookClickListener bookClickListener) {
        this.movies = movies;
        this.showBookButton = showBookButton;
        this.bookClickListener = bookClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.imgPoster.setImageResource(movie.getPosterRes());
        holder.txtName.setText(movie.getName());
        holder.txtGenreDuration.setText(movie.getGenre() + " • " + movie.getDuration() + " min");

        if (showBookButton) {
            holder.btnBookSeats.setVisibility(View.VISIBLE);
            holder.btnBookSeats.setOnClickListener(v -> bookClickListener.onBookClick(movie));
        } else {
            holder.btnBookSeats.setVisibility(View.GONE);
        }

        holder.btnTrailer.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailerUrl()));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return movies.size(); }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtName, txtGenreDuration;
        Button btnBookSeats, btnTrailer;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster        = itemView.findViewById(R.id.imgPoster);
            txtName          = itemView.findViewById(R.id.txtMovieName);
            txtGenreDuration = itemView.findViewById(R.id.txtGenreDuration);
            btnBookSeats     = itemView.findViewById(R.id.btnBookSeats);
            btnTrailer       = itemView.findViewById(R.id.btnTrailer);
        }
    }
}