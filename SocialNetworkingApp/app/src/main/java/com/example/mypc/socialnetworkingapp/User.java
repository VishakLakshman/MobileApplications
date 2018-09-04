package com.example.mypc.socialnetworkingapp;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    String fname;
    String lastname;
    String dob;
    String email;
    HashMap<String,Post> posts;
    HashMap<String,Friend> friends;
    HashMap<String,Request> requests;


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String,Post> getPosts() {
        return posts;
    }

    public void setPosts(HashMap<String,Post> posts) {
        this.posts = posts;
    }

    public HashMap<String, Friend> getFriends() {
        return friends;
    }

    public void setFriends(HashMap<String, Friend> friends) {
        this.friends = friends;
    }

    public HashMap<String, Request> getRequests() {
        return requests;
    }

    public void setRequests(HashMap<String, Request> requets) {
        this.requests = requets;
    }

    @Override
    public String toString() {
        return "User{" +
                "fname='" + fname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dob='" + dob + '\'' +
                ", email='" + email + '\'' +
                ", posts=" + posts +
                ", friends=" + friends +
                ", requests=" + requests +
                '}';
    }
}
