package com.example.mypc.musicsearchapp;

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
        ProgressBar progressBar;

        View newActivity;

        Activity newAct;

        public GetImage(View activity)
        {
            newActivity = activity;
        }

        public GetImage(Activity activity)
    {
        newAct = activity;
    }

    @Override
    protected void onPreExecute() {
        if(newActivity != null) {
            iv = (ImageView) newActivity.findViewById(R.id.imageViewAlbumArt);
            progressBar = (ProgressBar) newActivity.findViewById(R.id.progressBar);

            iv.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            iv = (ImageView) newAct.findViewById(R.id.imageViewDetail);
            progressBar = (ProgressBar) newAct.findViewById(R.id.progressBar2);

            iv.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
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

            progressBar.setVisibility(View.INVISIBLE);
            iv.setVisibility(View.VISIBLE);

            iv.setImageBitmap(s);
        }

    }