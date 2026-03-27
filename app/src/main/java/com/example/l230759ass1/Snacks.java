package com.example.l230759ass1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Snacks extends AppCompatActivity {

    private static final double PRICE_POPCORN = 8.99;
    private static final double PRICE_NACHOS  = 7.99;
    private static final double PRICE_DRINK   = 5.99;
    private static final double PRICE_CANDY   = 6.99;

    private int qtyPopcorn = 0;
    private int qtyNachos  = 0;
    private int qtyDrink   = 0;
    private int qtyCandy   = 0;

    private TextView txtQtyPopcorn, txtQtyNachos, txtQtyDrink, txtQtyCandy;

    private String movieName, selectedDate, seatLabels;
    private int seatCount;
    private double seatTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snacks);

        movieName    = getIntent().getStringExtra("movie_name");
        selectedDate = getIntent().getStringExtra("selected_date");
        seatCount    = getIntent().getIntExtra("seat_count", 0);
        seatTotal    = getIntent().getDoubleExtra("seat_total", 0.0);
        seatLabels   = getIntent().getStringExtra("seat_labels");

        txtQtyPopcorn = findViewById(R.id.txtQtyPopcorn);
        txtQtyNachos  = findViewById(R.id.txtQtyNachos);
        txtQtyDrink   = findViewById(R.id.txtQtyDrink);
        txtQtyCandy   = findViewById(R.id.txtQtyCandy);

        findViewById(R.id.btnPlusPopcorn).setOnClickListener(v  -> adjust(1,  1));
        findViewById(R.id.btnMinusPopcorn).setOnClickListener(v -> adjust(1, -1));
        findViewById(R.id.btnPlusNachos).setOnClickListener(v   -> adjust(2,  1));
        findViewById(R.id.btnMinusNachos).setOnClickListener(v  -> adjust(2, -1));
        findViewById(R.id.btnPlusDrink).setOnClickListener(v    -> adjust(3,  1));
        findViewById(R.id.btnMinusDrink).setOnClickListener(v   -> adjust(3, -1));
        findViewById(R.id.btnPlusCandy).setOnClickListener(v    -> adjust(4,  1));
        findViewById(R.id.btnMinusCandy).setOnClickListener(v   -> adjust(4, -1));

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            double snacksTotal = (qtyPopcorn * PRICE_POPCORN)
                    + (qtyNachos  * PRICE_NACHOS)
                    + (qtyDrink   * PRICE_DRINK)
                    + (qtyCandy   * PRICE_CANDY);

            Intent intent = new Intent(Snacks.this, TicketSummary.class);
            intent.putExtra("movie_name",    movieName);
            intent.putExtra("selected_date", selectedDate);
            intent.putExtra("seat_count",    seatCount);
            intent.putExtra("seat_total",    seatTotal);
            intent.putExtra("seat_labels",   seatLabels);
            intent.putExtra("snacks_total",  snacksTotal);
            intent.putExtra("qty_popcorn",   qtyPopcorn);
            intent.putExtra("qty_nachos",    qtyNachos);
            intent.putExtra("qty_drink",     qtyDrink);
            intent.putExtra("qty_candy",     qtyCandy);
            startActivity(intent);
        });
    }

    private void adjust(int item, int delta) {
        switch (item) {
            case 1:
                qtyPopcorn = Math.max(0, qtyPopcorn + delta);
                txtQtyPopcorn.setText(String.valueOf(qtyPopcorn));
                break;
            case 2:
                qtyNachos = Math.max(0, qtyNachos + delta);
                txtQtyNachos.setText(String.valueOf(qtyNachos));
                break;
            case 3:
                qtyDrink = Math.max(0, qtyDrink + delta);
                txtQtyDrink.setText(String.valueOf(qtyDrink));
                break;
            case 4:
                qtyCandy = Math.max(0, qtyCandy + delta);
                txtQtyCandy.setText(String.valueOf(qtyCandy));
                break;
        }
    }
}