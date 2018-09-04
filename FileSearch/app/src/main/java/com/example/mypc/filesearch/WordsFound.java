package com.example.mypc.filesearch;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordsFound extends AppCompatActivity {

    ArrayList<String> searchResultList;
    ArrayAdapter<String>  adapterResult;
    ListView resultView;

    Button finishButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_found);

        searchResultList = new ArrayList<String>();

        finishButton = (Button) findViewById(R.id.finishButton);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        if(getIntent().getExtras() != null)
        {
            searchResultList = (ArrayList<String>) getIntent().getExtras().getSerializable(MainActivity.RESULT_KEY);

        }

        resultView = (ListView) findViewById(R.id.resultListView);



        adapterResult = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, searchResultList);

        resultView.setAdapter(adapterResult);





    }
}
