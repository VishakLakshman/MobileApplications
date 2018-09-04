package com.example.mypc.triviaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    int finalScore=0;

    TextView scoretv,resultTV;
    ProgressBar scoreProgress;
    Button qButton,buttonTryAgain;

    ArrayList<Question> questionss;

    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        questionss = new ArrayList<Question>();

        scoretv = (TextView) findViewById(R.id.textView4);
        resultTV = (TextView) findViewById(R.id.resutText);

        scoreProgress = (ProgressBar) findViewById(R.id.scoreBar);
        scoreProgress.setMax(100);

        qButton = (Button) findViewById(R.id.qButton);
        buttonTryAgain = (Button) findViewById(R.id.tryAgainButton);



        if(getIntent().getExtras() != null)
        {
            finalScore = getIntent().getExtras().getInt(TriviaActivity.SCORE_KEY);
            count = getIntent().getExtras().getInt(TriviaActivity.COUNT_KEY);
           // questionss = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QUESTIONS_KEY);
        }

        int percentage = (finalScore*100)/count;

        scoretv.setText(percentage+"%");
        scoreProgress.setProgress(percentage);

        if(percentage == 100)
            resultTV.setText("Congratulations !! Try and see if you can get all the answers right again!");
        else
            resultTV.setText("Try again and see if you can get all the correct answers!");


        qButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatsActivity.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("EXIT", true);
                startActivity(i);

            }
        });

        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });




    }
}
