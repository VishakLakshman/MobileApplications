package com.example.mypc.filesearch;

/*
* Group 29
* Homework 2
* Name :
* 1. Akshay M Adagale 800987050
* 2. Vishak Lakshman Sanjeevikani Murugesh 800985356
*
* */

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    EditText searchEditText;
    ListView searchListView;
    Button addButton;
    Button searchButton;
    ProgressBar searchProgress;

    TextView temp;

    CheckBox matchCaseCheck;

    int macth_case=0;

    CustomAdapter customAdapter;

    ArrayList<String> fileLines;

    ArrayList<String> searchList;

    final static String FILENAME ="textfile.txt";

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchListView = (ListView) findViewById(R.id.searchListView);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        searchButton = (Button) findViewById(R.id.searchButton);
        addButton = (Button) findViewById(R.id.addButton);
        matchCaseCheck = (CheckBox) findViewById(R.id.matchCaseCheck);

        searchProgress = (ProgressBar) findViewById(R.id.progressBar);
        searchProgress.setMax(100);

        searchList =  new ArrayList<String>();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchword = searchEditText.getText().toString();
                searchList.add(searchword);
                searchEditText.setText("");
                customAdapter = new CustomAdapter(MainActivity.this, searchList);
                searchListView.setAdapter(customAdapter);
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String lineContent = null;
                    fileLines =  new ArrayList<String>();

                    if(matchCaseCheck.isChecked())
                        macth_case = 0;
                    else
                        macth_case = 1;
                    InputStream is = getAssets().open("textfile.txt");

                    BufferedReader inputStream = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                    while ((lineContent = inputStream.readLine()) != null) {
                        fileLines.add(lineContent.replace("\t", " "));
                    }

                    new FileSearchThread().execute(searchList);

                }catch(Exception e)
                {

                }
            }
        });


    }


    final static String RESULT_KEY = "result";


    public class FileSearchThread extends AsyncTask<ArrayList<String>, Integer, ArrayList<String>> {

        ArrayList<String> searchWords;

        int progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            temp =(TextView) findViewById(R.id.tenp);
            searchProgress = (ProgressBar) findViewById(R.id.progressBar);
            searchProgress.setMax(100);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            searchProgress.setProgress(values[0]);
        }

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... params) {

            ArrayList<String> searchResults = new ArrayList<String>();
            searchWords = params[0];

            for(int i=0;i<searchWords.size();i++) {
                searchResults.addAll(TextSearchUtil.SearchKeyWordInFile(fileLines, searchWords.get(i),macth_case));
                progress = (((i+1)*100)/searchWords.size());
                publishProgress(progress);
            }

            return searchResults;
        }


        @Override
        protected void onPostExecute(ArrayList<String> s) {

            //temp.setText("enteredfinal " +s.size()+" "+progress);

            if(s.size() == 0){
                Toast.makeText(MainActivity.this, "Word not found", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(MainActivity.this, WordsFound.class);
                i.putExtra(MainActivity.RESULT_KEY, s);
                startActivity(i);
            }
        }

    }

}
