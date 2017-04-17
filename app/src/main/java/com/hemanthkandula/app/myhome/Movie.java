package com.hemanthkandula.app.myhome;

/**
 * Created by Siva Subramanian L on 04-01-2017.
 */
public class Movie {
    private String title, genre , rank , name;

    public Movie() {
    }

    public Movie(String title, String genre, String rank, String name) {
        this.title = title;
        this.genre = genre;
        this.name = name;
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
