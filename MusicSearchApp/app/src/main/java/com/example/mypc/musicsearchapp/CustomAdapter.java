package com.example.mypc.musicsearchapp;

import android.app.Activity;
        import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
        import android.widget.Toast;

        import java.io.File;
        import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class CustomAdapter extends BaseAdapter {

    Activity context;
    ArrayList<Song> songList;

    CheckBox favcheck;
    ImageView iv;
    TextView tv,tv2;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    ArrayList<Song> favSongList;

    int flag;

    private static LayoutInflater inflater = null;

    public CustomAdapter(Activity context, ArrayList<Song> searchList,int flag){

        this.context = context;
        this.songList = searchList;
        this.flag=flag;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Song getItem(int i) {
        return songList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    Song selectedSong;
    int position=0;
    View userView;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        userView = view;
        userView = (userView == null) ? inflater.inflate(R.layout.row,viewGroup,false) : userView ;

        tv = (TextView) userView.findViewById(R.id.favSongNameTextView);
        tv2 = (TextView) userView.findViewById(R.id.favSongArtistTextView);
        iv = (ImageView) userView.findViewById(R.id.imageViewAlbumArt);
        favcheck = (CheckBox) userView.findViewById(R.id.checkBox1);

        pref = context.getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        selectedSong = songList.get(i);

        favSongList = new ArrayList<Song>();

        String s=pref.getString("songList",null);
        if(s!=null){
            favSongList = new ArrayList<Song>();
            favSongList = DataUtilsClass.parseString(pref.getString("songList",null));
        }

        if(DataUtilsClass.checkSong(selectedSong,favSongList)) {
            favcheck.setChecked(true);
        }
        else
            favcheck.setChecked(false);

        tv.setText (selectedSong.getName());
        tv2.setText(selectedSong.getArtist());
        try {
            new GetImage(userView).execute(selectedSong.getSmallImageURL());
        }
        catch (Exception e)
        {
            Log.e("ex",e.getMessage());
        }


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TrackDetails.class);

                View parentRow = (View) view.getParent();
                ListView lv = (ListView) parentRow.getParent();
                position = lv.getPositionForView(parentRow);

                intent.putExtra(SearchResults.SELECTED_SONG_KEY,songList.get(position));
                position = 0;
                context.startActivity(intent);
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TrackDetails.class);

                View parentRow = (View) view.getParent();
                ListView lv = (ListView) parentRow.getParent();
                position = lv.getPositionForView(parentRow);

                intent.putExtra(SearchResults.SELECTED_SONG_KEY,songList.get(position));
                position = 0;
                context.startActivity(intent);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TrackDetails.class);

                View parentRow = (View) view.getParent();
                ListView lv = (ListView) parentRow.getParent();
                position = lv.getPositionForView(parentRow);

                intent.putExtra(SearchResults.SELECTED_SONG_KEY,songList.get(position));
                position = 0;
                context.startActivity(intent);
            }
        });

        favcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View parentRow = (View) view.getParent();
                ListView lv = (ListView) parentRow.getParent();
                position = lv.getPositionForView(parentRow);

                String s=pref.getString("songList",null);

                CheckBox cb = (CheckBox) view;

                Boolean state = cb.isChecked();

                if(s!=null){
                    favSongList = new ArrayList<Song>();
                    favSongList = DataUtilsClass.parseString(pref.getString("songList",null));
                }

                if(state)
                {
                    Song t = songList.get(position);
                    if(!DataUtilsClass.checkSong(t,favSongList)) {
                        favSongList.add(t);
                        editor.putString("songList", favSongList.toString());
                        editor.commit();
                        Toast.makeText(context, "The song " + songList.get(position).getName() + " is added to favourites", Toast.LENGTH_LONG).show();
                    }
                    position = 0;
                }
                else
                {
                    if(!state) {
                        Song t = songList.get(position);
                        if (DataUtilsClass.checkSong(t, favSongList)) {

                            if (flag == 1) {
                                songList.remove(position);
                                favSongList.remove(position);
                                editor.putString("songList", favSongList.toString());
                                editor.putString("songList", songList.toString());
                                editor.commit();
                                notifyDataSetChanged();
                            }
                            else
                            if(flag == 0)
                            {
                                favSongList = DataUtilsClass.deleteSong(t,favSongList);
                                editor.putString("songList", favSongList.toString());
                                editor.commit();
                            }
                            Toast.makeText(context, "The song " + t.getName() + " is removed from favourites", Toast.LENGTH_LONG).show();
                        }
                        position = 0;
                    }
                }


            }
        });

        return userView;
    }
}
