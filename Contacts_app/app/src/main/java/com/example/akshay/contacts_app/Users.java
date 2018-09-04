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
import android.support.annotation.NonNull;

import java.io.Serializable;

public class Users implements Serializable,Comparable<Users> {

    String firstName;
    String lastName;
    String company;
    String phoneNumber;
    String email;
    String url;
    String address;
    String birthday;
    String nickName;
    String facebookProfileURL;
    String twitterProfileURL;
    String Skype;
    String youTube;
    String image;



    public Users(String firstName, String lastName, String company, String phoneNumber, String email, String url, String address, String birthday, String nickName, String facebookProfileURL, String twitterProfileURL, String skype, String youTube) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.url = url;
        this.address = address;
        this.birthday = birthday;
        this.nickName = nickName;
        this.facebookProfileURL = facebookProfileURL;
        this.twitterProfileURL = twitterProfileURL;
        Skype = skype;
        this.youTube = youTube;
    }

    public Users(){
        super();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFacebookProfileURL() {
        return facebookProfileURL;
    }

    public void setFacebookProfileURL(String facebookProfileURL) {
        this.facebookProfileURL = facebookProfileURL;
    }

    public String getTwitterProfileURL() {
        return twitterProfileURL;
    }

    public void setTwitterProfileURL(String twitterProfileURL) {
        this.twitterProfileURL = twitterProfileURL;
    }

    public String getSkype() {
        return Skype;
    }

    public void setSkype(String skype) {
        Skype = skype;
    }

    public String getYouTube() {
        return youTube;
    }

    public void setYouTube(String youTube) {
        this.youTube = youTube;
    }

    @Override
    public int compareTo(@NonNull Users users) {
        String b = users.getFirstName();
        String a = this.firstName;
        return a.compareTo(b);
    }
}
