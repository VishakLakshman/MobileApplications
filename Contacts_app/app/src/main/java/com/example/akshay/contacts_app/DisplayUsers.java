
/*
* Group 29
* Homework 2
* Name :
* 1. Akshay M Adagale 800987050
* 2. Vishak Lakshman Sanjeevikani Murugesh 800985356
* 
* */


package com.example.akshay.contacts_app;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayUsers extends AppCompatActivity {

    ArrayList<Users> contacts = new ArrayList<Users>();
    String type;
    int ipos;

    Users t;
    Intent i;
    AlertDialog alertDialog;

    final static String USER_KEY = "user";
    final static String POSITION_KEY = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);

        final ListView contactListView = (ListView) findViewById(R.id.contactList);

        UsersAdapter arrayAdapter;

        try {
            if (getIntent().getExtras() != null) {

                contacts = (ArrayList<Users>) getIntent().getExtras().getSerializable(MainActivity.CONTACT_KEY);
                type = getIntent().getExtras().getString(MainActivity.FLAG);

                arrayAdapter = new UsersAdapter(this, contacts);
                contactListView.setAdapter(arrayAdapter);


            }
        } catch (Exception e) {

        }

        alertDialog = new AlertDialog.Builder(DisplayUsers.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Do you really want to delete this ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        switch (type) {
            case "edit":
                contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        t = contacts.get(position);
                        i = new Intent(getBaseContext(), CreateNew.class);
                        i.putExtra(MainActivity.CONTACT_KEY, contacts);
                        i.putExtra(DisplayUsers.USER_KEY, t);
                        i.putExtra(DisplayUsers.POSITION_KEY,position);
                        i.putExtra(MainActivity.FLAG, "edit");
                        startActivity(i);
                        finish();
                    }
                });
                break;

            case "delete":
                contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        t = contacts.get(position);
                        ipos = position;
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        contacts.remove(ipos);
                                        i = new Intent(getBaseContext(), MainActivity.class);
                                        i.putExtra(MainActivity.CONTACT_KEY, contacts);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    }
                });
                break;
            case "display":
                contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        t = contacts.get(position);
                        i = new Intent(getBaseContext(), ContactsDisplay.class);
                        i.putExtra(DisplayUsers.USER_KEY, t);
                        startActivity(i);
                        finish();
                    }
                });
                break;

        }


    }

}
