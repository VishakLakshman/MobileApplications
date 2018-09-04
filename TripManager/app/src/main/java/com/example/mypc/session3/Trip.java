package com.example.mypc.session3;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;


public class Trip implements Serializable,Comparable<Trip> {

    private String name;
    private String cost;
    private ArrayList<Deal> deals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public ArrayList<Deal> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<Deal> deals) {
        this.deals = deals;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", deals=" + deals +
                '}';
    }

    @Override
    public int compareTo(@NonNull Trip trip) {
        return trip.getCost().compareTo(cost);
    }
}
