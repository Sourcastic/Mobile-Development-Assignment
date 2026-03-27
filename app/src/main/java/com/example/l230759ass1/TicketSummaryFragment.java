package com.example.l230759ass1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TicketSummaryFragment extends Fragment {

    private static final double TICKET_PRICE = 12.50;

    public static TicketSummaryFragment newInstance(String movieName, String selectedDate,
                                                    int seatCount, double seatTotal,
                                                    String seatLabels,
                                                    double snacksTotal,
                                                    int qtyPopcorn, int qtyNachos,
                                                    int qtyDrink, int qtyCandy) {
        TicketSummaryFragment fragment = new TicketSummaryFragment();
        Bundle args = new Bundle();
        args.putString("movie_name", movieName);
        args.putString("selected_date", selectedDate);
        args.putInt("seat_count", seatCount);
        args.putDouble("seat_total", seatTotal);
        args.putString("seat_labels", seatLabels);
        args.putDouble("snacks_total", snacksTotal);
        args.putInt("qty_popcorn", qtyPopcorn);
        args.putInt("qty_nachos", qtyNachos);
        args.putInt("qty_drink", qtyDrink);
        args.putInt("qty_candy", qtyCandy);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ticket_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args == null) return;

        String movieName = args.getString("movie_name");
        String selectedDate = args.getString("selected_date");
        int seatCount = args.getInt("seat_count");
        double seatTotal = args.getDouble("seat_total");
        String seatLabels = args.getString("seat_labels");
        double snacksTotal = args.getDouble("snacks_total");
        int qtyPopcorn = args.getInt("qty_popcorn");
        int qtyNachos = args.getInt("qty_nachos");
        int qtyDrink = args.getInt("qty_drink");
        int qtyCandy = args.getInt("qty_candy");

        ImageView poster = view.findViewById(R.id.imgMoviePoster);
        if (getString(R.string.lotr).equals(movieName)) {
            poster.setImageResource(R.drawable.rotk);
        } else if (getString(R.string.dune).equals(movieName)) {
            poster.setImageResource(R.drawable.dune1);
        } else {
            poster.setImageResource(R.drawable.barbie);
        }

        ((TextView) view.findViewById(R.id.txtSummaryMovieName)).setText(movieName);
        ((TextView) view.findViewById(R.id.txtSummaryDate)).setText(selectedDate != null ? selectedDate : "Today");

        LinearLayout ticketsContainer = view.findViewById(R.id.ticketsContainer);
        double pricePerSeat = seatCount > 0 ? seatTotal / seatCount : TICKET_PRICE;

        if (seatLabels != null && !seatLabels.isEmpty()) {
            for (String seat : seatLabels.split("\\|")) {
                LinearLayout row = new LinearLayout(requireContext());
                row.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.bottomMargin = 8;
                row.setLayoutParams(params);

                TextView label = new TextView(requireContext());
                label.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                label.setText(seat.trim());
                label.setTextColor(0xBFFFFFFF);
                label.setTextSize(14);

                TextView price = new TextView(requireContext());
                price.setText(String.format("$%.2f", pricePerSeat));
                price.setTextColor(0xFFFFFFFF);
                price.setTextSize(14);
                price.setTypeface(null, android.graphics.Typeface.BOLD);

                row.addView(label);
                row.addView(price);
                ticketsContainer.addView(row);
            }
        }

        TextView txtSnacksList = view.findViewById(R.id.txtSnacksList);
        TextView txtSnacksPrice = view.findViewById(R.id.txtSnacksPrice);

        StringBuilder snacksList = new StringBuilder();
        if (qtyPopcorn > 0) snacksList.append("x").append(qtyPopcorn).append(" Popcorn\n");
        if (qtyNachos > 0) snacksList.append("x").append(qtyNachos).append(" Nachos\n");
        if (qtyDrink > 0) snacksList.append("x").append(qtyDrink).append(" Soft Drink\n");
        if (qtyCandy > 0) snacksList.append("x").append(qtyCandy).append(" Candy Mix\n");

        if (snacksList.length() == 0) {
            txtSnacksList.setText("None selected");
            txtSnacksPrice.setText("$0.00");
        } else {
            txtSnacksList.setText(snacksList.toString().trim());
            txtSnacksPrice.setText(String.format("$%.2f", snacksTotal));
        }

        double grandTotal = seatTotal + snacksTotal;
        ((TextView) view.findViewById(R.id.txtGrandTotal))
                .setText(String.format("$%.2f USD", grandTotal));

        String ticketMessage = buildTicketMessage(
                movieName, selectedDate, seatLabels,
                seatTotal, snacksList.toString(), snacksTotal, grandTotal);

        // Save full ticket message in SharedPreferences
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("last_booking", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("last_ticket", ticketMessage)
                .apply();

        Button btnSendTicket = view.findViewById(R.id.btnSendTicket);
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