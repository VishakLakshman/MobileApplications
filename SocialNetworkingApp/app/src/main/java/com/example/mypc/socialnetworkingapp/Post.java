package com.example.mypc.socialnetworkingapp;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by My Pc on 19-11-2017.
 */

public class Post implements Comparable<Post>{

    String postedBy;
    String posttxt;
    String posttime;

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPosttxt() {
        return posttxt;
    }

    public void setPosttxt(String posttxt) {
        this.posttxt = posttxt;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String postTime) {
        this.posttime = postTime;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postedBy='" + postedBy + '\'' +
                ", posttxt='" + posttxt + '\'' +
                ", posttime='" + posttime + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull Post p) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date1, date2;
        int c=0;
        try {
            date1 = sdf.parse(posttime);
            date2 = sdf.parse(p.getPosttime());
            c = date2.compareTo(date1);
        }
        catch (Exception e)
        {

        }
        return c;
    }
}
