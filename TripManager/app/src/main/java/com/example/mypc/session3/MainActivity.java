package com.example.mypc.session3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainScreenFragment.MainScreeInteractionListener,AddTripFragment.AddTripInteractionListener {

    public static DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    public static ArrayList<Trip> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trips = new ArrayList<>();

        getSupportFragmentManager().beginTransaction().add(R.id.container,new MainScreenFragment(),"main").commit();

    }

    @Override
    public void onFragmentInteractionAdd() {

    }

    @Override
    public void onFragmentInteractionMain() {

    }
}
