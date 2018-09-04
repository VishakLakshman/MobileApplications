package com.example.mypc.triviaapp;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

/*
* Group 29
* Assignment 2 Homework 1
* Name :
* 1. Akshay M Adagale 800987050
* 2. Vishak Lakshman Sanjeevikani Murugesh 800985356
*
* */

public class MainActivity extends AppCompatActivity {

    final static String TRIVIA_QUESTIONS_URL = "http://dev.theappsdr.com/apis/trivia_json/index.php";

    final static String QUESTIONS_KEY = "questions";
    final static String END_String = "end";

    Button startButton, endButtton;

    ArrayList<Question> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.buttonStart);
        endButtton = (Button) findViewById(R.id.buttonExit);

        questionsList = new ArrayList<Question>();

        if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)){

            System.exit(0);
        }

        try {
            questionsList = new GetData(MainActivity.this).execute(TRIVIA_QUESTIONS_URL).get();
        }
        catch (Exception e){

        }


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, TriviaActivity.class);
                    i.putExtra(MainActivity.QUESTIONS_KEY, questionsList);
                    startActivity(i);
            }
        });

        endButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });



    }

    void initialiseQuestions(){
        Question q = new Question();
        ArrayList<String> c = new ArrayList<String>();

        //QuestionOne
        q.setId(0);
        q.setAnswer(1);
        q.setQuestionText("Who is the first President of the United States of America?");
        q.setImageURL("http://dev.theappsdr.com/apis/trivia_json/photos/georgewashington.png");

        c.add("George Washington");c.add("Thomas Jefferson");c.add("James Monroe");c.add("John Adams");c.add("Barack Obam");c.add("George Bush");c.add("Abraham Lincoln");c.add("John F. Kennedy");
        q.setChoices(c);

        questionsList.add(q);

        ArrayList<String> c1 = new ArrayList<String>();
        q = new Question();
        //QuestionTwo
        q.setId(1);
        q.setAnswer(3);
        q.setQuestionText("The above flag is for which country?");
        q.setImageURL("http://dev.theappsdr.com/apis/trivia_json/photos/egypt.png");

        c1.add("Spain");c1.add("Finland");c1.add("Egypt");
        q.setChoices(c1);

        questionsList.add(q);

        ArrayList<String> c2 = new ArrayList<String>();
        q = new Question();
        //QuestionThree
        q.setId(2);
        q.setAnswer(3);
        q.setQuestionText("Who was the first female pilot to fly solo across the Atlantic Ocean?");
        q.setImageURL("http://dev.theappsdr.com/apis/trivia_json/photos/earhart.png");

        c2.add("Bonnie Gann");c2.add("Elsie MacGill");c2.add("Amelia Earhart");c2.add("Linda Godwin");
        q.setChoices(c2);

        questionsList.add(q);


        ArrayList<String> c3 = new ArrayList<String>();
        q = new Question();
        //QuestionFour
        q.setId(3);
        q.setAnswer(4);
        q.setQuestionText("The name of the soccer player in the above photo is?");
        q.setImageURL("http://dev.theappsdr.com/apis/trivia_json/photos/messi.png");

        c3.add("Cristiano Ronaldo");c3.add("David Beckham");c3.add("Carlos Tevez");c3.add("Lionel Messi");
        q.setChoices(c3);

        questionsList.add(q);


        ArrayList<String> c4 = new ArrayList<String>();
        q = new Question();
        //QuestionFive
        q.setId(4);
        q.setAnswer(3);
        q.setQuestionText("The above map is for which country?");
        q.setImageURL("http://dev.theappsdr.com/apis/trivia_json/photos/italy.png");

        c4.add("UK");c4.add("France");c4.add("Italy");c4.add("Spain");c4.add("Romania");
        q.setChoices(c4);

        questionsList.add(q);

    }

}
