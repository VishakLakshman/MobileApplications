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
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Akshay on 15-Sep-17.
 */

public class CreateNew extends MainActivity {

    EditText firstName, lastName, company, email, url, address, birthday, nickname, facebookProfileURL, twitterProfileURL, skype, youTubeChannel, phoneNumber;
    Button save;
    ImageView image;
    Users users;
    String type;
    int position;

    ArrayList<Users> contactArray = new ArrayList<Users>();

    final static int REQUEST_IMAGE_CAPTURE = 1;

    Bitmap temp;
    Users t;
    StringBuffer filename;

    public String photoPath="asd";

    Calendar bDatePicker = Calendar.getInstance();


    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_contact);

        firstName = (EditText) findViewById(R.id.editTextFirstName);
        lastName = (EditText) findViewById(R.id.editTextLastName);
        company = (EditText) findViewById(R.id.editTextCompany);
        email = (EditText) findViewById(R.id.editTextEmail);
        url = (EditText) findViewById(R.id.editTextURL);
        address = (EditText) findViewById(R.id.editTextAddress);
        birthday = (EditText) findViewById(R.id.editTextBirthday);
        nickname = (EditText) findViewById(R.id.editTextNickname);
        facebookProfileURL = (EditText) findViewById(R.id.editTextFacebookProfileURL);
        twitterProfileURL = (EditText) findViewById(R.id.editTextTwitterProfileURL);
        skype = (EditText) findViewById(R.id.editTextSkype);
        youTubeChannel = (EditText) findViewById(R.id.editTextYouTubeChannel);
        phoneNumber = (EditText) findViewById(R.id.editTextPhone);

        image = (ImageView) findViewById(R.id.imageButton2);

        try {
            if (getIntent().getExtras() != null) {
                type = getIntent().getExtras().getString(MainActivity.FLAG);
                contactArray = (ArrayList<Users>) getIntent().getExtras().getSerializable(MainActivity.CONTACT_KEY);
                if (type.equals("edit")) {
                    t = (Users) getIntent().getExtras().getSerializable(DisplayUsers.USER_KEY);

                    firstName.setText(t.getFirstName());
                    lastName.setText(t.getLastName());
                    company.setText(t.getCompany());
                    email.setText(t.getEmail());
                    url.setText(t.getUrl());
                    address.setText(t.getAddress());
                    birthday.setText(t.getBirthday());
                    nickname.setText(t.getNickName());
                    facebookProfileURL.setText(t.facebookProfileURL);
                    twitterProfileURL.setText(t.getTwitterProfileURL());
                    skype.setText(t.getSkype());
                    youTubeChannel.setText(t.getYouTube());
                    phoneNumber.setText(t.getPhoneNumber());

                    position = getIntent().getExtras().getInt(DisplayUsers.POSITION_KEY);
                    if(!t.getImage().equals("asd")) {
                        File imgFile = new File(t.getImage());

                        if (imgFile.exists()) {

                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                            image.setImageBitmap(myBitmap);

                        }
                    }

                }
            }
        } catch (Exception e) {

        }

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                bDatePicker.set(Calendar.YEAR, year);
                bDatePicker.set(Calendar.MONTH, monthOfYear);
                bDatePicker.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                birthday = updateLabel(birthday);
            }

        };

        birthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateNew.this, date, bDatePicker.get(Calendar.YEAR), bDatePicker.get(Calendar.MONTH),
                        bDatePicker.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });


        users = new Users();


        save = (Button) findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                try {

                    if((firstName.getText().toString().equals("")) || (lastName.getText().toString().equals("")) ||(phoneNumber.getText().toString().equals(""))) {
                        Toast.makeText(CreateNew.this, "Firstname, lastname and phone are mandatory", Toast.LENGTH_LONG).show();
                    }
                    else {
                        users.setFirstName(firstName.getText().toString());
                        users.setLastName((lastName.getText().toString()));
                        users.setCompany((company.getText().toString()));
                        users.setEmail(email.getText().toString());
                        users.setPhoneNumber(phoneNumber.getText().toString());
                        users.setUrl(url.getText().toString());
                        users.setAddress(address.getText().toString());
                        users.setBirthday(birthday.getText().toString());
                        users.setNickName(nickname.getText().toString());
                        users.setFacebookProfileURL(facebookProfileURL.getText().toString());
                        users.setTwitterProfileURL(twitterProfileURL.getText().toString());
                        users.setSkype(skype.getText().toString());
                        users.setYouTube(youTubeChannel.getText().toString());
                        users.setImage(photoPath);


                        if (type.equals("create"))
                            contactArray.add(users);
                        else {
                            contactArray.remove(position);
                            contactArray.add(users);
                        }
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra(MainActivity.CONTACT_KEY, contactArray);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(CreateNew.this, "Firstname, lastname and phone are mandatory", Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });

    }

    private EditText updateLabel(EditText editText) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(bDatePicker.getTime()));
        return editText;
    }

    /*private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/
    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            File imgFile = new  File(photoPath);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                image.setImageBitmap(myBitmap);

            }

        }

    }

    private File createImageFile() {

        File imagev = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp;
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imagev = File.createTempFile(imageFileName, ".jpg", storageDir);


            photoPath = imagev.getAbsolutePath();
            return imagev;
        } catch (Exception e) {

        }
        return imagev;
    }

}