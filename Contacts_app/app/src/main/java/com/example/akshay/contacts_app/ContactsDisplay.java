/*
* Group 29
* Homework 2
* Name :
* 1. Akshay M Adagale 800987050
* 2. Vishak Lakshman Sanjeevikani Murugesh 800985356
* 
* */


package com.example.akshay.contacts_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ContactsDisplay extends AppCompatActivity {


    TextView firstName, lastName, company, email, url, address, birthday, nickname, facebookProfileURL, twitterProfileURL, skype, youTubeChannel, phoneNumber;
    ImageView imageDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_display);


        firstName = (TextView) findViewById(R.id.displayFirstName);
        lastName = (TextView) findViewById(R.id.displayLastName);
        company = (TextView) findViewById(R.id.displayCompany);
        email = (TextView) findViewById(R.id.displayEmail);
        url = (TextView) findViewById(R.id.displayURL);
        address = (TextView) findViewById(R.id.displayAddress);
        birthday = (TextView) findViewById(R.id.displayBirthday);
        nickname = (TextView) findViewById(R.id.displayNickname);
        facebookProfileURL = (TextView) findViewById(R.id.displayFacebookProfileURL);
        twitterProfileURL = (TextView) findViewById(R.id.displayTwitterProfileURL);
        skype = (TextView) findViewById(R.id.displaySkype);
        youTubeChannel = (TextView) findViewById(R.id.displayYouTubeChannel);
        phoneNumber = (TextView) findViewById(R.id.displayPhone);

        imageDisplay = (ImageView) findViewById(R.id.displayImage);


        Users t;

        try {
            if (getIntent().getExtras() != null) {

                t = (Users) getIntent().getExtras().getSerializable(DisplayUsers.USER_KEY);

                firstName.setText("First Name: "+t.getFirstName());
                lastName.setText("Last Name: "+t.getLastName());
                company.setText("Company: "+t.getCompany());
                email.setText("Email: "+t.getEmail());
                url.setText("URL: "+t.getUrl());
                address.setText("Address: "+t.getAddress());
                birthday.setText("Birthday: "+t.getBirthday());
                nickname.setText("Nickname: "+t.getNickName());
                facebookProfileURL.setText("Twitter: "+t.facebookProfileURL);
                twitterProfileURL.setText("Twitter: "+t.getTwitterProfileURL());
                skype.setText("Skype: "+t.getSkype());
                youTubeChannel.setText("Youtube: "+t.getYouTube());
                phoneNumber.setText("Phone: "+t.getPhoneNumber());

                Linkify.addLinks(url,Linkify.WEB_URLS);
                Linkify.addLinks(facebookProfileURL,Linkify.WEB_URLS);
                Linkify.addLinks(twitterProfileURL,Linkify.WEB_URLS);
                Linkify.addLinks(skype,Linkify.WEB_URLS);
                Linkify.addLinks(youTubeChannel,Linkify.WEB_URLS);

                if(!t.getImage().equals("asd")) {
                    File imgFile = new File(t.getImage());

                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        imageDisplay.setImageBitmap(myBitmap);

                    }
                }

            }
        } catch (Exception e) {

        }
    }
}
