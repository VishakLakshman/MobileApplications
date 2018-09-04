package com.example.mypc.musicsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TrackDetails extends AppCompatActivity {

    Song selectedSong;

    TextView songNameSelected, songArtistSelected, songURLSelected;

    ListView simialrsongsLV;

    ArrayList<Song> similarsongList;
    CustomAdapter custom;

    final static String SIMILAR_URL_START = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist=";
    final static String SIMILAR_URL_MID = "&track=";
    final static String SIMILAR_URL_END = "&api_key=d7222ab1d0e1cbbf173abdce8c51244d&format=json &limit=10";

    StringBuffer similarSongSearchUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        songNameSelected = (TextView) findViewById(R.id.textViewNameDetail);
        songArtistSelected = (TextView) findViewById(R.id.textViewArtistDetail);
        songURLSelected = (TextView) findViewById(R.id.textViewUrlDetail);

        simialrsongsLV = (ListView) findViewById(R.id.listViewSimilar);

        Linkify.addLinks(songURLSelected,Linkify.WEB_URLS);

        similarSongSearchUrl = new StringBuffer();

        if (getIntent().getExtras() != null) {
            selectedSong = (Song) getIntent().getExtras().getSerializable(SearchResults.SELECTED_SONG_KEY);
            similarSongSearchUrl.append(TrackDetails.SIMILAR_URL_START);
            similarSongSearchUrl.append(selectedSong.getArtist());
            similarSongSearchUrl.append(TrackDetails.SIMILAR_URL_MID);
            similarSongSearchUrl.append(selectedSong.getName());
            similarSongSearchUrl.append(TrackDetails.SIMILAR_URL_END);
        }

        songNameSelected.setText(selectedSong.getName());
        songArtistSelected.setText(selectedSong.getArtist());
        songURLSelected.setText(selectedSong.getSongURL());

        try {
            new GetImage(TrackDetails.this).execute(selectedSong.getLargeImageURL());
            similarsongList = new GetMusicData(TrackDetails.this).execute(similarSongSearchUrl.toString()).get();

            custom = new CustomAdapter(TrackDetails.this,similarsongList,0);
            simialrsongsLV.setAdapter(custom);
        }
        catch (Exception e)
        {
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
