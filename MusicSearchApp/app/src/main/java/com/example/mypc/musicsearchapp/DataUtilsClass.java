package com.example.mypc.musicsearchapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;


public class DataUtilsClass {

    public static ArrayList<Song> parsePersons(String in) throws JSONException {

        ArrayList<Song> iList = new ArrayList<Song>();
        try {
            JSONObject root = new JSONObject(in);

            JSONObject resultJson = root.getJSONObject("results");

            JSONObject trackmatchesJson = resultJson.getJSONObject("trackmatches");

            JSONArray jsonArray = trackmatchesJson.getJSONArray("track");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Song song = new Song();

                song.setName(jsonObject.getString("name"));
                song.setArtist(jsonObject.getString("artist"));
                song.setSongURL(jsonObject.getString("url"));

                JSONArray imageJSONArray = jsonObject.getJSONArray("image");

                for (int j =0;j<imageJSONArray.length();j++) {
                    JSONObject tempJSON = imageJSONArray.getJSONObject(j);
                    String size = tempJSON.getString("size");
                    if(size.equals("small"))
                    {
                        song.setSmallImageURL(tempJSON.getString("#text"));
                    }
                    if(size.equals("large"))
                    {
                        song.setLargeImageURL(tempJSON.getString("#text"));
                    }
                }

                iList.add(song);
            }
        } catch (Exception e) {


        }

        return iList;

    }

    public static ArrayList<Song> parseSimilarList(InputStream in) throws JSONException {

        ArrayList<Song> iList = new ArrayList<Song>();

        Song song = new Song();

        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();

            parser.setInput(in,"UTF-8");
            String temp="";
            int count = 0;

            int event = parser.getEventType();

            while(event != XmlPullParser.END_DOCUMENT)
            {
                switch(event)
                {
                    case XmlPullParser.START_TAG :
                        if(parser.getName().equals("track"))
                        {
                            song = new Song();
                        }
                        else
                        if(parser.getName().equals("name")){
                            temp = parser.nextText().trim();
                            count++;
                            Log.e("name ", temp);
                            if(count%2 == 1)
                              song.setName(temp);
                            else
                                song.setArtist(temp);
                        }
                        else
                        if(parser.getName().equals("url")){
                            temp = parser.nextText().trim();
                            song.setSongURL(temp);
                        }
                        else
                        if(parser.getName().equals("image") && (parser.getAttributeValue(null, "size").toString().equals("small"))) {
                            temp = parser.nextText().trim();
                            song.setSmallImageURL(temp);
                        }
                        else
                          if(parser.getName().equals("image")&& (parser.getAttributeValue(null, "size").toString().equals("large"))){
                              temp = parser.nextText().trim();
                              song.setLargeImageURL(temp);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("artist"))
                        {

                        }
                        else
                        if(parser.getName().equals("track")){
                            iList.add(song);
                            song = null;
                        }

                    default:break;

                }
                event = parser.next();
            }
        }
        catch(Exception e)
        {
            Log.e("exception ", e.getMessage());
        }

        return iList;

    }


    public static ArrayList<Song> parseString(String s){

        ArrayList<Song> output = new ArrayList<Song>();
        Song tempSong = new Song();

        String listString = s.substring(1, s.length() - 1);

        StringTokenizer token = new StringTokenizer(listString,"{");

        String t="";

        ArrayList<String> t1 = new ArrayList<String>();

        while(token.hasMoreTokens())
        {
            t = token.nextToken();
            if(t.length() > 4 && token.hasMoreTokens())
                t=t.substring(0, t.length() - 7);
            else
            if(!token.hasMoreTokens())
                t=t.substring(0, t.length() - 1);
            else
                continue;
            t1.add(t);
        }

        ArrayList<String> t2 = new ArrayList<String>();

        for(int i =0 ;i<t1.size();i++)
        {
            token = new StringTokenizer(t1.get(i),",");
            while(token.hasMoreTokens())
            {
                t= token.nextToken();
                t2.add(t);
            }

        }

        for(int i =0;i<t2.size();i++)
        {
            tempSong = new Song();

            t = t2.get(i);
            t = t.substring(6,t.length()-1);
            tempSong.setName(t);

            i++;

            t = t2.get(i);
            t = t.substring(10,t.length()-1);
            tempSong.setSongURL(t);

            i++;

            t = t2.get(i);
            t = t.substring(9,t.length()-1);
            tempSong.setArtist(t);

            i++;

            t = t2.get(i);
            t = t.substring(16,t.length()-1);
            tempSong.setSmallImageURL(t);

            i++;

            t = t2.get(i);
            t = t.substring(16,t.length()-1);
            tempSong.setLargeImageURL(t);

            output.add(tempSong);
        }


        return output;

    }

    public static Boolean checkSong(Song s, ArrayList<Song> songsList)
    {
        Boolean result = false;

        if(songsList.size() == 0)
            return false;

        for(int i=0;i<songsList.size();i++)
        {
            Song t = songsList.get(i);

            if(t.getName().equals(s.getName()) && t.getArtist().equals(s.getArtist()))
                result = true;
        }

        return result;
    }

    public static ArrayList<Song> deleteSong(Song s, ArrayList<Song> songsList)
    {
        if(songsList.size() == 0)
            return songsList;

        for(int i=0;i<songsList.size();i++)
        {
            Song t = songsList.get(i);

            if(t.getName().equals(s.getName()) && t.getArtist().equals(s.getArtist()))
                songsList.remove(i);
        }

        return songsList;
    }

}
