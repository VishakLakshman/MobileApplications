package com.example.mypc.triviaapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetData extends AsyncTask<String, Void, ArrayList<Question>> {

    StringBuffer stringBuilder;

    ImageView imageView;
    ProgressBar pb;
    Button startButton;

    Activity newActivity;

    public GetData(Activity activity)
    {
        newActivity = activity;
    }

    @Override
    protected void onPreExecute() {

        imageView = (ImageView) newActivity.findViewById(R.id.imageView);
        pb = (ProgressBar) newActivity.findViewById(R.id.progressBarStart);
        startButton = (Button) newActivity.findViewById(R.id.buttonStart);

        startButton.setClickable(false);

        pb.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);

    }

    @Override
            protected ArrayList<Question> doInBackground(String... params) {


                BufferedReader bufferedReader = null;

                try {
                    URL url = new URL(params[0]);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");

                    stringBuilder = new StringBuffer();

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    return DataUtilsClass.parsePersons(stringBuilder.toString());

                } catch (Exception e) {

                }
                return new ArrayList<Question>();
            }


            int position;

            @Override
            protected void onPostExecute(ArrayList<Question> s) {

                startButton.setClickable(true);

                pb.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);

            }

        }