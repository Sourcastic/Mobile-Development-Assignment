package com.example.l230759ass1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TicketSummaryFragment extends Fragment {

    private String selectedDate = "Today";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now_showing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup rgDate = view.findViewById(R.id.rgDateToggle);
        rgDate.setOnCheckedChangeListener((group, checkedId) ->
                selectedDate = checkedId == R.id.rbTomorrow ? "Tomorrow" : "Today");

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Lord of the Rings: Return of the King",
                "Fantasy", 201, R.drawable.rotk,
                "https://www.youtube.com/watch?v=r5X-hFf6Bwo"));
        movies.add(new Movie("Dune: Part 1",
                "Sci-Fi", 155, R.drawable.dune1,
                "https://www.youtube.com/watch?v=n9xhJrPXop4"));
        movies.add(new Movie("Barbie",
                "Comedy", 114, R.drawable.barbie,
                "https://www.youtube.com/watch?v=pBk4NYhWNMM"));



        RecyclerView rv = view.findViewById(R.id.rvNowShowing);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(new MovieAdapter(movies, true, movie -> {
            MainActivity activity = (MainActivity) requireActivity();
            activity.setMovieData(movie.getName(), selectedDate);
            activity.navigateTo(new SeatSelectionFragment(), true);
        }));
    }
}