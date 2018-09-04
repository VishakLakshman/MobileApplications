/*
* Group 29
* Homework 2
* Name :
* 1. Akshay M Adagale 800987050
* 2. Vishak Lakshman Sanjeevikani Murugesh 800985356
* 
* */

package com.example.akshay.contacts_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    Button createNew, editContact, deleteContact, displayContact, finish;
    ArrayList<Users> contactList = new ArrayList<Users>();

    final static String CONTACT_KEY = "contact";
    final static String FLAG = "FLAG";
    String function;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNew = (Button) findViewById(R.id.button);
        editContact = (Button) findViewById(R.id.button2);
        deleteContact = (Button) findViewById(R.id.button3);
        displayContact = (Button) findViewById(R.id.button4);
        finish = (Button) findViewById(R.id.button5);

        try{
        if (getIntent().getExtras() != null) {

            contactList = (ArrayList<Users>) getIntent().getExtras().getSerializable(MainActivity.CONTACT_KEY);
            Collections.sort(contactList);

        }
        }
        catch(Exception e)
        {

        }

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), CreateNew.class);
                i.putExtra(MainActivity.CONTACT_KEY,contactList);
                i.putExtra(MainActivity.FLAG, "create");
                startActivity(i);
            }

        });

        editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), DisplayUsers.class);
                i.putExtra(MainActivity.CONTACT_KEY, contactList);
                i.putExtra(MainActivity.FLAG, "edit");
                startActivity(i);
            }
        });

        deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), DisplayUsers.class);
                i.putExtra(MainActivity.CONTACT_KEY, contactList);
                i.putExtra(MainActivity.FLAG, "delete");
                startActivity(i);
            }
        });



        displayContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), DisplayUsers.class);
                i.putExtra(MainActivity.CONTACT_KEY, contactList);
                i.putExtra(MainActivity.FLAG, "display");
                startActivity(i);
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

    }

}
