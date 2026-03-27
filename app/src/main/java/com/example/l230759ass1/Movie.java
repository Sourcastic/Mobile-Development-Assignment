package com.example.l230759ass1;

public class Movie {
    private final String name;
    private final String genre;
    private final int duration;
    private final int posterRes;
    private final String trailerUrl;

    public Movie(String name, String genre, int duration, int posterRes, String trailerUrl) {
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.posterRes = posterRes;
        this.trailerUrl = trailerUrl;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public int getPosterRes() {
        return posterRes;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }
}