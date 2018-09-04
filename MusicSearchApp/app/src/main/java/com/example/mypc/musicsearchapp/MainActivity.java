package com.example.mypc.musicsearchapp;
/*
* Group 29
* Homework 5
* Name :
* 1. Akshay M Adagale 800987050
* 2. Vishak Lakshman Sanjeevikani Murugesh 800985356
*
* */
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    final static String START_URL="http://ws.audioscrobbler.com/2.0/?method=track.search&track=";
    final static String END_URL="&api_key=d7222ab1d0e1cbbf173abdce8c51244d&limit=20&format=json";

    final static String SONGLIST_KEY="songlist";

    StringBuffer songSearch;

    ArrayList<Song> songList;

    EditText searchText;
    Button searchButton;
    ListView favList;

    CustomAdapter adapter;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    ArrayList<Song> favSongsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songSearch = new StringBuffer();
        songList = new ArrayList<Song>();

        searchText = (EditText) findViewById(R.id.searchEditText);
        searchButton =(Button) findViewById(R.id.buttonSearch);
        favList = (ListView) findViewById(R.id.favListView);

        if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)){
            System.exit(0);
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                songSearch.append(START_URL);
                songSearch.append(searchText.getText().toString());
                songSearch.append(END_URL);
                try {
                    songList = new GetMusicData(MainActivity.this).execute(songSearch.toString()).get();
                    Intent i = new Intent(MainActivity.this,SearchResults.class);
                    i.putExtra(MainActivity.SONGLIST_KEY,songList);
                    startActivity(i);
                    //Toast.makeText(MainActivity.this,"asdasd"+songList.size(),Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Log.e("error",e.getMessage());
                }

            }
        });

        favSongsList = new ArrayList<Song>();

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        String s = pref.getString("songList",null);

       try {

           if (s != null) {
               favSongsList = DataUtilsClass.parseString(pref.getString("songList", null));
               adapter = new CustomAdapter(MainActivity.this, favSongsList, 1);
               favList.setAdapter(adapter);
           }
       }
       catch (Exception e){
           Log.e("ex",e.getMessage());
       }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_action, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                return true;

            case R.id.action_quit:
                Intent intent = new Intent(this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
