package com.example.mypc.musicsearchapp;

import java.io.Serializable;

/**
 * Created by My Pc on 13-10-2017.
 */

public class Song implements Serializable {

    String name;
    String songURL;
    String artist;
    String smallImageURL;
    String largeImageURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", songURL='" + songURL + '\'' +
                ", artist='" + artist + '\'' +
                ", smallImageURL='" + smallImageURL + '\'' +
                ", largeImageURL='" + largeImageURL + '\'' +
                '}';
    }


}
