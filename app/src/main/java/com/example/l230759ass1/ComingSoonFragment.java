package com.example.l230759ass1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComingSoonFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coming_soon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Super Mario Galaxy Movie", "Adventure Comedy", 98, R.drawable.mario, "https://www.youtube.com/watch?v=LX9kXRRJlPw "));
        movies.add(new Movie("Dune Part Three", "Sci-fi", 291, R.drawable.dune3, "https://www.youtube.com/watch?v=3_9vCamtuPY"));
        movies.add(new Movie("Micheal", "Biopic", 127, R.drawable.micheal, "https://www.youtube.com/watch?v=3zOLzsbOleM"));

        RecyclerView rv = view.findViewById(R.id.rvComingSoon);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(new MovieAdapter(movies, true, movie -> {
            MainActivity activity = (MainActivity) requireActivity();
            activity.setMovieData(movie.getName(), "Coming Soon", movie);
            activity.navigateTo(new SeatSelectionFragment(), true);
        }));
    }
}