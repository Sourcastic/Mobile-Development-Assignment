package com.example.l230759ass1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SnacksFragment extends Fragment {

    private static final String ARG_MOVIE = "arg_movie";
    private static final String ARG_DATE = "arg_date";
    private static final String ARG_SEAT_COUNT = "arg_seat_count";
    private static final String ARG_SEAT_TOTAL = "arg_seat_total";
    private static final String ARG_SEAT_LABELS = "arg_seat_labels";

    private String movieName;
    private String date;
    private int seatCount;
    private double seatTotal;
    private String seatLabels;

    private ArrayList<Snack> snackList;

    public SnacksFragment() { }

    public static SnacksFragment newInstance(String movieName, String date,
                                             int seatCount, double seatTotal, String seatLabels) {
        SnacksFragment fragment = new SnacksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE, movieName);
        args.putString(ARG_DATE, date);
        args.putInt(ARG_SEAT_COUNT, seatCount);
        args.putDouble(ARG_SEAT_TOTAL, seatTotal);
        args.putString(ARG_SEAT_LABELS, seatLabels);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieName = getArguments().getString(ARG_MOVIE);
            date = getArguments().getString(ARG_DATE);
            seatCount = getArguments().getInt(ARG_SEAT_COUNT);
            seatTotal = getArguments().getDouble(ARG_SEAT_TOTAL);
            seatLabels = getArguments().getString(ARG_SEAT_LABELS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_snacks, container, false);

        ListView listView = view.findViewById(R.id.lvSnacks);
        Button btnContinue = view.findViewById(R.id.btnConfirm);

        snackList = new ArrayList<>();
        snackList.add(new Snack(R.drawable.popcorn, "Popcorn", 5.00));
        snackList.add(new Snack(R.drawable.nachos, "Nachos", 6.50));
        snackList.add(new Snack(R.drawable.soft_drink, "Soft Drink", 3.00));
        snackList.add(new Snack(R.drawable.candy_mix, "Candy Mix", 4.00));

        SnackAdapter adapter = new SnackAdapter(requireContext(), snackList);
        listView.setAdapter(adapter);

        btnContinue.setOnClickListener(v -> {
            int qtyPopcorn = snackList.get(0).getQuantity();
            int qtyNachos = snackList.get(1).getQuantity();
            int qtyDrink = snackList.get(2).getQuantity();
            int qtyCandy = snackList.get(3).getQuantity();

            double snacksTotal = 0;
            for (Snack s : snackList) {
                snacksTotal += s.getPrice() * s.getQuantity();
            }

            TicketSummaryFragment tsFragment = TicketSummaryFragment.newInstance(
                    movieName,
                    date,
                    seatCount,
                    seatTotal,
                    seatLabels,
                    snacksTotal,
                    qtyPopcorn,
                    qtyNachos,
                    qtyDrink,
                    qtyCandy
            );

            MainActivity activity = (MainActivity) requireActivity();
            activity.navigateTo(tsFragment, true);
        });

        return view;
    }
}