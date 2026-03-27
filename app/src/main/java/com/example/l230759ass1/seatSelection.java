package com.example.l230759ass1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class seatSelection extends AppCompatActivity {

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
            {false, false, true,  false, false, true,  false, false},
            {true,  false, false, false, true,  true,  false, false},
            {false, false, false, false, false, false, false, true },
            {false, true,  true,  false, false, false, false, false},
            {false, false, false, true,  false, false, true,  true },
            {true,  false, false, false, true,  false, false, false},
            {false, false, true,  true,  false, false, false, false}
    };

    private boolean[][] bookedSeats;
    private final boolean[][] selectedSeats = new boolean[7][8];
    private View[][] seatViews;

    private TextView txtSeatSummary;
    private Button btnProceedSnacks;
    private Button btnBookSeats;
    private String movieName;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_selection);

        movieName = getIntent().getStringExtra("movie_name");
        String rawDate = getIntent().getStringExtra("selected_date");
        date = (rawDate == null) ? "Today" : rawDate;

        TextView txtTitle = findViewById(R.id.txtMovieTitle);
        txtTitle.setText(movieName);

        TextView txtDate = findViewById(R.id.txtSelectedDate);
        txtDate.setText(date);

        ImageView banner = findViewById(R.id.imgMovieBanner);
        if ("Lord of the Rings: Return of the King".equals(movieName)) {
            banner.setImageResource(R.drawable.rotk);
        } else if ("Dune: Part 1".equals(movieName)) {
            banner.setImageResource(R.drawable.dune1);
        } else {
            banner.setImageResource(R.drawable.barbie);
        }

        txtSeatSummary = findViewById(R.id.txtSeatSummary);
        btnProceedSnacks = findViewById(R.id.btnProceedSnacks);
        btnBookSeats = findViewById(R.id.btnBookSeats);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        bookedSeats = new boolean[7][8];
        for (int r = 0; r < 7; r++)
            bookedSeats[r] = originalBookedSeats[r].clone();

        btnProceedSnacks.setOnClickListener(v -> {
            int count = countSelected();
            double total = count * TICKET_PRICE;
            String seatLabels = buildSeatLabels();
            lockSeats();
            Intent intent = new Intent(seatSelection.this, Snacks.class);
            intent.putExtra("movie_name", movieName);
            intent.putExtra("selected_date", date);
            intent.putExtra("seat_count", count);
            intent.putExtra("seat_total", total);
            intent.putExtra("seat_labels", seatLabels);
            startActivity(intent);
        });

        btnBookSeats.setOnClickListener(v -> {
            int count = countSelected();
            double total = count * TICKET_PRICE;
            String seatLabels = buildSeatLabels();
            Intent intent = new Intent(seatSelection.this, TicketSummary.class);
            intent.putExtra("movie_name", movieName);
            intent.putExtra("selected_date", date);
            intent.putExtra("seat_count", count);
            intent.putExtra("seat_total", total);
            intent.putExtra("seat_labels", seatLabels);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int r = 0; r < 7; r++) {
            bookedSeats[r] = originalBookedSeats[r].clone();
            for (int c = 0; c < 8; c++)
                selectedSeats[r][c] = false;
        }
        bindSeatViews();
        updateSummary();
    }

    private void bindSeatViews() {
        seatViews = new View[7][8];
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 8; c++) {
                View seat = findViewById(seatIds[r][c]);
                seatViews[r][c] = seat;
                seat.setOnClickListener(null);
                if (bookedSeats[r][c]) {
                    seat.setBackground(ContextCompat.getDrawable(this, R.drawable.seat_booked));
                    seat.setClickable(false);
                    seat.setFocusable(false);
                } else {
                    seat.setBackground(ContextCompat.getDrawable(this, R.drawable.seat_available));
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
        seatViews[r][c].setBackground(ContextCompat.getDrawable(this,
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
                    seatViews[r][c].setBackground(ContextCompat.getDrawable(this, R.drawable.seat_booked));
                    seatViews[r][c].setClickable(false);
                    seatViews[r][c].setFocusable(false);
                }
            }
        }
        updateSummary();
    }
}