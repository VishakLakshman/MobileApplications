package com.example.mypc.triviaapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.net.HttpURLConnection;
import java.net.URL;

public class GetImage extends AsyncTask<String,Void,Bitmap> {

        ImageView iv;
        ProgressBar progressBarImage;

        Activity newActivity;

        public GetImage(Activity activity)
        {
            newActivity = activity;
        }

    @Override
    protected void onPreExecute() {
        iv = (ImageView) newActivity.findViewById(R.id.imageViewQuestion);
        progressBarImage = (ProgressBar) newActivity.findViewById(R.id.imageLoadBar);

        progressBarImage.setVisibility(View.VISIBLE);
        iv.setVisibility(View.INVISIBLE);
    }

    @Override
        protected Bitmap doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream((httpURLConnection.getInputStream()));
                return image;
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            progressBarImage.setVisibility(View.INVISIBLE);
            iv.setVisibility(View.VISIBLE);

            iv.setImageBitmap(s);

        }

    }