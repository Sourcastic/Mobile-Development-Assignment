package com.example.l230759ass1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TicketSummary extends AppCompatActivity {

    private static final double TICKET_PRICE = 12.50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_summary);

        String movieName = getIntent().getStringExtra("movie_name");
        String selectedDate = getIntent().getStringExtra("selected_date");
        int seatCount = getIntent().getIntExtra("seat_count", 0);
        double seatTotal = getIntent().getDoubleExtra("seat_total", 0.0);
        String seatLabels = getIntent().getStringExtra("seat_labels");
        double snacksTotal = getIntent().getDoubleExtra("snacks_total", 0.0);
        int qtyPopcorn = getIntent().getIntExtra("qty_popcorn", 0);
        int qtyNachos = getIntent().getIntExtra("qty_nachos", 0);
        int qtyDrink = getIntent().getIntExtra("qty_drink", 0);
        int qtyCandy = getIntent().getIntExtra("qty_candy", 0);

        ImageView poster = findViewById(R.id.imgMoviePoster);
        if (getString(R.string.lotr).equals(movieName)) {
            poster.setImageResource(R.drawable.rotk);
        } else if (getString(R.string.dune).equals(movieName)) {
            poster.setImageResource(R.drawable.dune1);
        } else {
            poster.setImageResource(R.drawable.barbie);
        }

        ((TextView) findViewById(R.id.txtSummaryMovieName)).setText(movieName);
        ((TextView) findViewById(R.id.txtSummaryDate)).setText(
                selectedDate != null ? selectedDate : "Today");

        LinearLayout ticketsContainer = findViewById(R.id.ticketsContainer);
        double pricePerSeat = seatCount > 0 ? seatTotal / seatCount : TICKET_PRICE;

        if (seatLabels != null && !seatLabels.isEmpty()) {
            for (String seat : seatLabels.split("\\|")) {
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.bottomMargin = 8;
                row.setLayoutParams(params);

                TextView label = new TextView(this);
                label.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                label.setText(seat.trim());
                label.setTextColor(0xBFFFFFFF);
                label.setTextSize(14);

                TextView price = new TextView(this);
                price.setText(String.format("$%.2f", pricePerSeat));
                price.setTextColor(0xFFFFFFFF);
                price.setTextSize(14);
                price.setTypeface(null, android.graphics.Typeface.BOLD);

                row.addView(label);
                row.addView(price);
                ticketsContainer.addView(row);
            }
        }

        StringBuilder snacksList = new StringBuilder();
        if (qtyPopcorn > 0) snacksList.append("x").append(qtyPopcorn).append(" Popcorn\n");
        if (qtyNachos > 0) snacksList.append("x").append(qtyNachos).append(" Nachos\n");
        if (qtyDrink > 0) snacksList.append("x").append(qtyDrink).append(" Soft Drink\n");
        if (qtyCandy > 0) snacksList.append("x").append(qtyCandy).append(" Candy Mix\n");

        TextView txtSnacksList = findViewById(R.id.txtSnacksList);
        TextView txtSnacksPrice = findViewById(R.id.txtSnacksPrice);

        if (snacksList.length() == 0) {
            txtSnacksList.setText("None selected");
            txtSnacksPrice.setText("$0.00");
        } else {
            txtSnacksList.setText(snacksList.toString().trim());
            txtSnacksPrice.setText(String.format("$%.2f", snacksTotal));
        }

        double grandTotal = seatTotal + snacksTotal;
        ((TextView) findViewById(R.id.txtGrandTotal))
                .setText(String.format("$%.2f USD", grandTotal));

        String ticketMessage = buildTicketMessage(
                movieName, selectedDate, seatLabels,
                seatTotal, snacksList.toString(), snacksTotal, grandTotal);

        Button btnSendTicket = findViewById(R.id.btnSendTicket);
        btnSendTicket.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, ticketMessage);
            startActivity(Intent.createChooser(shareIntent, "Send Ticket via"));
        });
    }

    private String buildTicketMessage(String movie, String date, String seatLabels,
                                      double seatTotal, String snacks,
                                      double snacksTotal, double grandTotal) {
        StringBuilder sb = new StringBuilder();
        sb.append("CineFAST Booking Summary\n");
        sb.append("================================\n\n");
        sb.append("Movie: ").append(movie).append("\n");
        sb.append("Date:  ").append(date).append("\n");
        sb.append("Hall:  1st\n");
        sb.append("Time:  20:00\n\n");
        sb.append("Tickets:\n");
        if (seatLabels != null && !seatLabels.isEmpty()) {
            for (String seat : seatLabels.split("\\|")) {
                sb.append("  ").append(seat.trim()).append("\n");
            }
        }
        sb.append("Ticket Total: $").append(String.format("%.2f", seatTotal)).append("\n\n");
        if (!snacks.trim().isEmpty()) {
            sb.append("Snacks & Drinks:\n").append(snacks.trim()).append("\n");
            sb.append("Snacks Total: $").append(String.format("%.2f", snacksTotal)).append("\n\n");
        }
        sb.append("GRAND TOTAL: $").append(String.format("%.2f", grandTotal)).append(" USD\n");
        return sb.toString();
    }
}