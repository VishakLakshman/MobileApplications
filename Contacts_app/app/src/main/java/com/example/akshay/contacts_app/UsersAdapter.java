/*
* Group 29
* Homework 2
* Name :
* 1. Akshay M Adagale 800987050
* 2. Vishak Lakshman Sanjeevikani Murugesh 800985356
* 
* */


package com.example.akshay.contacts_app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by My Pc on 17-09-2017.
 */

public class UsersAdapter extends BaseAdapter {

    Activity context;
    ArrayList<Users> contacts;
    private static LayoutInflater inflater = null;

    public UsersAdapter(Activity context, ArrayList<Users> contact){

        this.context = context;
        this.contacts = contact;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Users getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View userView = view;
        userView = (userView == null) ? inflater.inflate(R.layout.activity_list_view,null) : userView ;

        TextView contactName = (TextView) userView.findViewById(R.id.contactName);
        TextView contactPhone = (TextView) userView.findViewById(R.id.contactPhone);
        ImageView imageD = (ImageView) userView.findViewById(R.id.imageView);

        Users selectedUser = contacts.get(i);

        if(!selectedUser.getImage().equals("asd")) {
            File imgFile = new File(selectedUser.getImage());

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imageD.setImageBitmap(myBitmap);

            }
        }

        contactName.setText(selectedUser.getFirstName()+" "+selectedUser.getLastName());
        contactPhone.setText(selectedUser.getPhoneNumber());

        return userView;
    }
}
