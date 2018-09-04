/*
* Group 29
* Assignment 2 In Class
* Name :
* 1. Akshay M Adagale 800987050
* 2. Vishak Lakshman Sanjeevikani Murugesh 800985356
*
* */

package com.example.akshay.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    OnClickFunctions o = new OnClickFunctions();

    Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9, buttonDot, buttonPlus, buttonMinus, buttonMultiply, buttonDivide, buttonEqual, buttonClear;
    EditText resultBar;

    StringBuffer numberTwo = new StringBuffer("");
    StringBuffer numberOne = new StringBuffer("");
    String operator = "no";
    String temp;

    Double output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        resultBar = (EditText)findViewById(R.id.editTextDisplayResult);
        b0 = (Button)findViewById(R.id.button0);
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        b5 = (Button)findViewById(R.id.button5);
        b6 = (Button)findViewById(R.id.button6);
        b7 = (Button)findViewById(R.id.button7);
        b8 = (Button)findViewById(R.id.button8);
        b9 = (Button)findViewById(R.id.button9);
        buttonDot = (Button) findViewById(R.id.buttonDot);
        buttonPlus = (Button)findViewById(R.id.buttonPlus);
        buttonMinus = (Button)findViewById(R.id.buttonMinus);
        buttonMultiply = (Button)findViewById(R.id.buttonMultiplication);
        buttonDivide = (Button)findViewById(R.id.buttonDivide);
        buttonClear = (Button)findViewById(R.id.buttonClear);
        buttonEqual = (Button)findViewById(R.id.buttonShowResult);


        b0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonZero(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonOne(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonTwo(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });
        b3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonThree(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });
        b4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonFour(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });
        b5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonFive(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });
        b6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonSix(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });
        b7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonSeven(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });
        b8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonEight(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });
        b9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonNine(numberOne,numberTwo,operator);
                resultBar.setText(temp);
            }
        });
        buttonDot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                temp = o.onClickButtonDot(numberOne,numberTwo,operator);
                resultBar = o.changeMaxLimit(15, resultBar);
                resultBar.setText(temp);
            }
        });
        buttonPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                operator = o.onClickButtonPlus(operator);
                resultBar = o.changeMaxLimit(14, resultBar);
                resultBar.setText("");
            }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(numberOne.toString().equals("")){
                    numberOne = new StringBuffer("");
                    numberOne.append("-");
                    resultBar.setText(numberOne.toString());
                }
                else
                {
                    if(!(operator.equals("no"))){
                        numberTwo = new StringBuffer("");
                       numberTwo.append("-");
                        resultBar.setText(numberTwo.toString());
                    }
                    else
                    {
                      operator = o.onClickButtonMinus(operator);
                      resultBar = o.changeMaxLimit(14, resultBar);
                      resultBar.setText("");
                    }
                }
            }
        });
        buttonMultiply.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                operator = o.onClickButtonMultiply(operator);
                resultBar = o.changeMaxLimit(14, resultBar);
                resultBar.setText("");
            }
        });
        buttonDivide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                operator = o.onClickButtonDivide(operator);
                resultBar = o.changeMaxLimit(14, resultBar);
                resultBar.setText("");
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                numberOne = new StringBuffer("");
                numberTwo = new StringBuffer("");
                operator = "no";
                resultBar = o.changeMaxLimit(14, resultBar);
                resultBar.setText("0");
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    Double inputOne = Double.parseDouble(numberOne.toString());
                    Double inputTwo = Double.parseDouble(numberTwo.toString());
                    switch (operator){
                    case "+":
                            if(inputOne == 0.0){
                                output = output + inputTwo;
                            }
                            else
                                output = inputOne + inputTwo;
                            numberOne = new StringBuffer("0");
                            numberTwo = new StringBuffer("");
                            operator = "no";
                            resultBar.setText(output.toString());
                            break;

                    case "-":
                            if(inputOne == 0.0){
                                output = output - inputTwo;
                            }
                            else
                                output = inputOne - inputTwo;
                            numberOne = new StringBuffer("0");
                            numberTwo = new StringBuffer("");
                            operator = "no";
                            resultBar.setText(output.toString());
                            break;

                        case "*":
                            if(inputOne == 0.0){
                                output = output * inputTwo;
                            }
                            else
                                output = inputOne * inputTwo;
                            numberOne = new StringBuffer("0");
                            numberTwo = new StringBuffer("");
                            operator = "no";
                            resultBar.setText(output.toString());
                            break;

                        case "/":
                            if(inputOne == 0.0){
                                output = output / inputTwo;
                            }
                            else
                                output = inputOne / inputTwo;
                            numberOne = new StringBuffer("0");
                            numberTwo = new StringBuffer("");
                            operator = "no";
                            resultBar.setText(output.toString());
                            break;
                        default:
                            resultBar.setText("0");
                    }

                }catch (Exception e)
                {
                    resultBar.setText("0");
                }
                }


        });


    }
}
