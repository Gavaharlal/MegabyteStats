package com.dolgikh.myapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Song {
    private final String author;
    private final String title;
    private final String dateAdding;


    Song(String songSignature, long unixTime) {
        String[] args = songSignature.split(" - ");
        String author = args[0];
        String title = args[1];
        this.author = author;
        this.title = title;
        this.dateAdding = parseUnixToDate(unixTime);
    }

    Song(String author, String title, String date) {
        this.author = author;
        this.title = title;
        this.dateAdding = date;
    }

    String getAuthor() {
        return author;
    }

    String getTitle() {
        return title;
    }

    String getDateAdding() {
        return dateAdding;
    }

    @Override
    public String toString() {
        return author + " - " + title;
    }

    private String parseUnixToDate(long unixTime) {
        String pattern = "dd.MM.yyyy H hh:mm";
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(unixTime));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Song)) {
            return false;
        } else {
            Song song = (Song) obj;
            return author.equals(song.author) && title.equals(song.title);
        }
    }
}
