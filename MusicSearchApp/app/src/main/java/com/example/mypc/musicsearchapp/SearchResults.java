package com.example.mypc.musicsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResults extends AppCompatActivity {

    ArrayList<Song> songResults;

    ArrayList<Song> favList;

    ListView resultsLV;

    CustomAdapter adapter;

    int pos;

    final static String SELECTED_SONG_KEY = "selectedsong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        resultsLV = (ListView) findViewById(R.id.resultsListView);

        if (getIntent().getExtras() != null) {

            songResults = (ArrayList<Song>) getIntent().getExtras().getSerializable(MainActivity.SONGLIST_KEY);
            adapter = new CustomAdapter(SearchResults.this,songResults,0);
            resultsLV.setAdapter(adapter);
        }

        resultsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos =i;
                Intent intent = new Intent(SearchResults.this,TrackDetails.class);
                startActivity(intent);
            }
        });

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
