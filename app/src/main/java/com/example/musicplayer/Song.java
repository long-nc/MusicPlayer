package com.example.musicplayer;

public class Song {
    private long id;
    private String title;
    private String artist;
    private int duration;
    private String data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Song(long id, String title, String artist, int duration, String data) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.data = data;
    }
}
