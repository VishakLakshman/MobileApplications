package com.example.mypc.session3;

import java.io.Serializable;

/**
 * Created by My Pc on 11-12-2017.
 */

public class Location implements Serializable {

    private Double Lat;
    private Double Lon;

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLon() {
        return Lon;
    }

    public void setLon(Double lon) {
        Lon = lon;
    }

    @Override
    public String toString() {
        return "Location{" +
                "Lat=" + Lat +
                ", Lon=" + Lon +
                '}';
    }
}
