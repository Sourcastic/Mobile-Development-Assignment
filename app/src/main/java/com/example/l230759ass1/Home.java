/*
package com.example.l230759ass1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private String selectedDate = "Today";

    private void openTrailer(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void bookSeats(String movieName) {
        Intent intent = new Intent(Home.this, seatSelection.class);
        intent.putExtra("movie_name", movieName);
        intent.putExtra("selected_date", selectedDate);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        RadioGroup rgDate = findViewById(R.id.rgDateToggle);
        rgDate.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbTomorrow) {
                selectedDate = "Tomorrow";
            } else {
                selectedDate = "Today";
            }
        });

        Button btnBook1 = findViewById(R.id.btnBook1);
        Button btnTrailer1 = findViewById(R.id.btnTrailer1);
        Button btnBook2 = findViewById(R.id.btnBook2);
        Button btnTrailer2 = findViewById(R.id.btnTrailer2);
        Button btnBook3 = findViewById(R.id.btnBook3);
        Button btnTrailer3 = findViewById(R.id.btnTrailer3);

        btnBook1.setOnClickListener(v -> bookSeats("Lord of the Rings: Return of the King"));
        btnTrailer1.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=r5X-hFf6Bwo"));
        btnBook2.setOnClickListener(v -> bookSeats("Dune: Part 1"));
        btnTrailer2.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=n9xhJrPXop4"));
        btnBook3.setOnClickListener(v -> bookSeats("Barbie"));
        btnTrailer3.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=pBk4NYhWNMM"));
    }
}*/
