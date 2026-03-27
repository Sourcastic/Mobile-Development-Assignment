package com.example.l230759ass1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class SeatSelectionFragment extends Fragment {

    private static final double TICKET_PRICE = 12.50;

    private final String[] ROW_LETTERS = {"A", "B", "C", "D", "E", "F", "G"};

    private final int[][] seatIds = {
            {R.id.s_1_1, R.id.s_1_2, R.id.s_1_3, R.id.s_1_4, R.id.s_1_5, R.id.s_1_6, R.id.s_1_7, R.id.s_1_8},
            {R.id.s_2_1, R.id.s_2_2, R.id.s_2_3, R.id.s_2_4, R.id.s_2_5, R.id.s_2_6, R.id.s_2_7, R.id.s_2_8},
            {R.id.s_3_1, R.id.s_3_2, R.id.s_3_3, R.id.s_3_4, R.id.s_3_5, R.id.s_3_6, R.id.s_3_7, R.id.s_3_8},
            {R.id.s_4_1, R.id.s_4_2, R.id.s_4_3, R.id.s_4_4, R.id.s_4_5, R.id.s_4_6, R.id.s_4_7, R.id.s_4_8},
            {R.id.s_5_1, R.id.s_5_2, R.id.s_5_3, R.id.s_5_4, R.id.s_5_5, R.id.s_5_6, R.id.s_5_7, R.id.s_5_8},
            {R.id.s_6_1, R.id.s_6_2, R.id.s_6_3, R.id.s_6_4, R.id.s_6_5, R.id.s_6_6, R.id.s_6_7, R.id.s_6_8},
            {R.id.s_7_1, R.id.s_7_2, R.id.s_7_3, R.id.s_7_4, R.id.s_7_5, R.id.s_7_6, R.id.s_7_7, R.id.s_7_8}
    };

    private final boolean[][] originalBookedSeats = {
            {false, false, true, false, false, true, false, false},
            {true, false, false, false, true, true, false, false},
            {false, false, false, false, false, false, false, true},
            {false, true, true, false, false, false, false, false},
            {false, false, false, true, false, false, true, true},
            {true, false, false, false, true, false, false, false},
            {false, false, true, true, false, false, false, false}
    };
    private final boolean[][] selectedSeats = new boolean[7][8];
    private boolean[][] bookedSeats;
    private View[][] seatViews;

    private TextView txtSeatSummary;
    private Button btnProceedSnacks;
    private Button btnBookSeats;
    private Button btnComingSoon;
    private Button btnWatchTrailer;

    private String movieName;
    private String date;
    private String trailerUrl;
    private boolean isComingSoon;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.seat_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) requireActivity();
        movieName = activity.getMovieName();
        date = activity.getSelectedDate();
        trailerUrl = activity.getTrailerUrl();
        isComingSoon = activity.getIsComingSoon();

        TextView txtTitle = view.findViewById(R.id.txtMovieTitle);
        txtTitle.setText(movieName);

        TextView txtDate = view.findViewById(R.id.txtSelectedDate);
        txtDate.setText(date != null ? date : "Today");

        ImageView banner = view.findViewById(R.id.imgMovieBanner);
        Movie movie = ((MainActivity) requireActivity()).getCurrentMovie();
        if (movie != null) {
            banner.setImageResource(movie.getPosterRes());
        } else {
            banner.setImageResource(R.drawable.barbie);
        }

        txtSeatSummary = view.findViewById(R.id.txtSeatSummary);
        btnProceedSnacks = view.findViewById(R.id.btnProceedSnacks);
        btnBookSeats = view.findViewById(R.id.btnBookSeats);
        btnComingSoon = view.findViewById(R.id.btnComingSoon);
        btnWatchTrailer = view.findViewById(R.id.btnWatchTrailer);

        ImageButton btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        bookedSeats = new boolean[7][8];
        for (int r = 0; r < 7; r++)
            bookedSeats[r] = originalBookedSeats[r].clone();


        if (isComingSoon) {
            setupComingSoonMode(view);
        } else {
            setupNowShowingMode();
        }
    }

    private void setupNowShowingMode() {
        btnComingSoon.setVisibility(View.GONE);
        btnWatchTrailer.setVisibility(View.GONE);
        btnProceedSnacks.setVisibility(View.VISIBLE);
        btnBookSeats.setVisibility(View.VISIBLE);

        btnProceedSnacks.setAlpha(0.4f);
        btnProceedSnacks.setEnabled(false);
        btnBookSeats.setAlpha(0.4f);
        btnBookSeats.setEnabled(false);

        bindSeatViews();

        btnProceedSnacks.setOnClickListener(v -> {
            int count = countSelected();
            double total = count * TICKET_PRICE;
            String seatLabels = buildSeatLabels();
            lockSeats();
            MainActivity activity = (MainActivity) requireActivity();
            activity.setSeatData(count, total, seatLabels);
            activity.navigateTo(new SnacksFragment(), true);
        });

        btnBookSeats.setOnClickListener(v -> {
            int count = countSelected();
            double total = count * TICKET_PRICE;
            String seatLabels = buildSeatLabels();
            lockSeats();
            Toast.makeText(requireContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();
            MainActivity activity = (MainActivity) requireActivity();
            activity.setSeatData(count, total, seatLabels);
            activity.setSnacksData(0, 0, 0, 0, 0);
            activity.navigateTo(new TicketSummaryFragment(), true);
        });
    }

    private void setupComingSoonMode(View view) {
        btnProceedSnacks.setVisibility(View.GONE);
        btnBookSeats.setVisibility(View.GONE);
        btnComingSoon.setVisibility(View.VISIBLE);
        btnWatchTrailer.setVisibility(View.VISIBLE);

        btnComingSoon.setEnabled(false);
        btnComingSoon.setAlpha(0.4f);

        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 8; c++) {
                View seat = view.findViewById(seatIds[r][c]);
                if (seat != null) {
                    seat.setClickable(false);
                    seat.setFocusable(false);
                    seat.setAlpha(0.4f);
                }
            }
        }

        txtSeatSummary.setVisibility(View.GONE);

        btnWatchTrailer.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
            startActivity(intent);
        });
    }

    private void bindSeatViews() {
        seatViews = new View[7][8];
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 8; c++) {
                View seat = requireView().findViewById(seatIds[r][c]);
                seatViews[r][c] = seat;
                seat.setOnClickListener(null);
                if (bookedSeats[r][c]) {
                    seat.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.seat_booked));
                    seat.setClickable(false);
                    seat.setFocusable(false);
                } else {
                    seat.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.seat_available));
                    seat.setClickable(true);
                    seat.setFocusable(true);
                    final int fr = r, fc = c;
                    seat.setOnClickListener(v -> toggleSeat(fr, fc));
                }
            }
        }
    }

    private void toggleSeat(int r, int c) {
        selectedSeats[r][c] = !selectedSeats[r][c];
        seatViews[r][c].setBackground(ContextCompat.getDrawable(requireContext(),
                selectedSeats[r][c] ? R.drawable.seat_selected : R.drawable.seat_available));
        updateSummary();
    }

    private void updateSummary() {
        int count = countSelected();
        double total = count * TICKET_PRICE;
        txtSeatSummary.setText(count + " seat" + (count != 1 ? "s" : "") +
                " selected  •  Total: $" + String.format("%.2f", total));
        boolean hasSeats = count > 0;
        btnProceedSnacks.setEnabled(hasSeats);
        btnProceedSnacks.setAlpha(hasSeats ? 1.0f : 0.4f);
        btnBookSeats.setEnabled(hasSeats);
        btnBookSeats.setAlpha(hasSeats ? 1.0f : 0.4f);
    }

    private int countSelected() {
        int count = 0;
        for (int r = 0; r < 7; r++)
            for (int c = 0; c < 8; c++)
                if (selectedSeats[r][c]) count++;
        return count;
    }

    private String buildSeatLabels() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 8; c++) {
                if (selectedSeats[r][c]) {
                    if (sb.length() > 0) sb.append("|");
                    sb.append("Row ").append(ROW_LETTERS[r]).append(", Seat ").append(c + 1);
                }
            }
        }
        return sb.toString();
    }

    private void lockSeats() {
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 8; c++) {
                if (selectedSeats[r][c]) {
                    selectedSeats[r][c] = false;
                    bookedSeats[r][c] = true;
                    seatViews[r][c].setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.seat_booked));
                    seatViews[r][c].setClickable(false);
                    seatViews[r][c].setFocusable(false);
                }
            }
        }
        updateSummary();
    }
}