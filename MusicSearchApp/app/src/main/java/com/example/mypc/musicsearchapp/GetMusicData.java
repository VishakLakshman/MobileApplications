package com.example.mypc.musicsearchapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetMusicData extends AsyncTask<String, Void, ArrayList<Song>> {

    StringBuffer stringBuilder;

    Activity newActivity;

    public GetMusicData(Activity activity)
    {
        newActivity = activity;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
            protected ArrayList<Song> doInBackground(String... params) {

                BufferedReader bufferedReader = null;

                try {
                    String n = "https://api.themoviedb.org/3/search/movie/?query=batman&api_key=83644c8867f48c0123b1a0ea479895db&page=1";
                    URL url = new URL(n);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");


                    if(params[0].contains("method=track.getsimilar"))
                    {
                        httpURLConnection.connect();
                        int statusCode= httpURLConnection.getResponseCode();

                        if(statusCode == HttpURLConnection.HTTP_OK)
                        {
                            InputStream in = httpURLConnection.getInputStream();
                            return DataUtilsClass.parseSimilarList(in);

                        }
                    }

                    stringBuilder = new StringBuffer();

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    Log.e("asd", stringBuilder.toString());

                    return DataUtilsClass.parsePersons(stringBuilder.toString());

                } catch (Exception e) {
                    Log.e("asd",e.getMessage());
                }
                return new ArrayList<Song>();
            }


            int position;

            @Override
            protected void onPostExecute(ArrayList<Song> s) {

            }

        }