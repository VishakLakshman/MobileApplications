package com.example.mypc.triviaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {

    ArrayList<Question> questions;

    TextView questionNumberTextView,timeTextView,getQuestionTextView;
    Button quitButton,nextButton;

    RadioGroup choicesRG;

    Question tempQuestion;

    GetImage getImage;

    ProgressBar imageLoading;
    ImageView iv;

    int questioNumber;
    int answer;

    int score=0;

    final static String SCORE_KEY = "score";
    final static String COUNT_KEY = "count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        questions = new ArrayList<Question>();

        questionNumberTextView = (TextView) findViewById(R.id.textQuestionNumber);
        timeTextView = (TextView) findViewById(R.id.textTimer);
        getQuestionTextView = (TextView) findViewById(R.id.textViewQuestion);

        quitButton = (Button) findViewById(R.id.buttonQuit);
        nextButton = (Button) findViewById(R.id.buttonNext);

        choicesRG = (RadioGroup) findViewById(R.id.radioGroup);

        imageLoading = (ProgressBar) findViewById(R.id.imageLoadBar);
        iv = (ImageView) findViewById(R.id.imageViewQuestion);

        if(getIntent().getExtras() != null)
        {
            questions = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QUESTIONS_KEY);
        }

        questioNumber = 0;

        tempQuestion = questions.get(questioNumber++);

        questionNumberTextView.setText("Q"+(questioNumber));
        getQuestionTextView.setText(tempQuestion.getQuestionText());

        if(tempQuestion.getImageURL() != null)
        {
            new GetImage(TriviaActivity.this).execute(tempQuestion.getImageURL());
        }
        else
        {
            imageLoading.setVisibility(View.INVISIBLE);
            iv.setImageDrawable(getResources().getDrawable(R.drawable.default_image));
        }

        createChoices(tempQuestion.getChoices());

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TriviaActivity.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("EXIT", true);
                startActivity(i);
            }
        });

        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeTextView.setText("Timeleft: " + (millisUntilFinished / 1000)+" seconds");
            }

            public void onFinish() {
                Intent i = new Intent(TriviaActivity.this,StatsActivity.class);
                i.putExtra(TriviaActivity.SCORE_KEY,score);
                i.putExtra(TriviaActivity.COUNT_KEY,questions.size());
                i.putExtra(MainActivity.QUESTIONS_KEY,questions);
                startActivity(i);
            }
        }.start();


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv.setImageBitmap(null);

                if(choicesRG.getCheckedRadioButtonId() != -1)
                {
                    answer = choicesRG.getCheckedRadioButtonId();
                    if(answer == tempQuestion.getAnswer())
                        score++;
                }

                if(questioNumber < questions.size()) {

                    tempQuestion = questions.get(questioNumber++);

                    questionNumberTextView.setText("Q" + (questioNumber));
                    getQuestionTextView.setText(tempQuestion.getQuestionText());

                    createChoices(tempQuestion.getChoices());

                    if(tempQuestion.getImageURL() != null)
                    {
                        new GetImage(TriviaActivity.this).execute(tempQuestion.getImageURL());
                    }
                    else
                    {
                        imageLoading.setVisibility(View.INVISIBLE);
                        iv.setImageDrawable(getResources().getDrawable(R.drawable.default_image));
                    }

                }
                else
                {
                    Intent i = new Intent(TriviaActivity.this,StatsActivity.class);
                    i.putExtra(TriviaActivity.SCORE_KEY,score);
                    i.putExtra(TriviaActivity.COUNT_KEY,questions.size());
                    i.putExtra(MainActivity.QUESTIONS_KEY,questions);
                    startActivity(i);
                }

            }
        });





    }

    void createChoices(ArrayList<String> choices)
    {
        choicesRG = (RadioGroup) findViewById(R.id.radioGroup);
        choicesRG.removeAllViews();

        RadioGroup.LayoutParams rprms;

        for(int i=0;i<choices.size();i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choices.get(i));
            radioButton.setId(i+1);
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            choicesRG.addView(radioButton, rprms);
        }

    }
}
