package com.example.l230759ass1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    private String movieName;
    private String selectedDate;
    private int seatCount;
    private double seatTotal;
    private String seatLabels;
    private double snacksTotal;
    private int qtyPopcorn, qtyNachos, qtyDrink, qtyCandy;
    private String trailerUrl;
    private Movie currentMovie;

    public void setMovieData(String name, String dateOrStatus, Movie movie) {
        this.movieName = name;
        this.selectedDate = dateOrStatus;
        this.currentMovie = movie;
        this.trailerUrl = movie.getTrailerUrl();
    }

    public boolean getIsComingSoon() {
        return selectedDate != null && selectedDate.equals("Coming Soon");
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public double getSeatTotal() {
        return seatTotal;
    }

    public String getSeatLabels() {
        return seatLabels;
    }

    public double getSnacksTotal() {
        return snacksTotal;
    }

    public int getQtyPopcorn() {
        return qtyPopcorn;
    }

    public int getQtyNachos() {
        return qtyNachos;
    }

    public int getQtyDrink() {
        return qtyDrink;
    }

    public int getQtyCandy() {
        return qtyCandy;
    }

    // EXISTING SETTERS (unchanged)
    public void setSeatData(int count, double total, String labels) {
        this.seatCount = count;
        this.seatTotal = total;
        this.seatLabels = labels;
    }

    public void setSnacksData(double total, int popcorn, int nachos, int drink, int candy) {
        this.snacksTotal = total;
        this.qtyPopcorn = popcorn;
        this.qtyNachos = nachos;
        this.qtyDrink = drink;
        this.qtyCandy = candy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            navigateTo(new HomeFragment(), false);
        }
    }

    public void navigateTo(Fragment fragment, boolean addToBackStack) {
        androidx.fragment.app.FragmentTransaction tx =
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, fragment);
        if (addToBackStack) tx.addToBackStack(null);
        tx.commit();
    }
}