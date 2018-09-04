package com.example.mypc.session3;


import android.location.*;

import java.io.Serializable;

public class Deal implements Serializable{

    private Double Cost;
    private Location Location;
    private String Duration;
    private String Place;

    public Double getCost() {
        return Cost;
    }

    public void setCost(Double cost) {
        Cost = cost;
    }

    public com.example.mypc.session3.Location getLocation() {
        return Location;
    }

    public void setLocation(com.example.mypc.session3.Location location) {
        Location = location;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "Cost=" + Cost +
                ", Location=" + Location +
                ", Duration='" + Duration + '\'' +
                ", Place='" + Place + '\'' +
                '}';
    }
}
