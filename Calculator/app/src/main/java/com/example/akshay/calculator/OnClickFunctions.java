package com.example.akshay.calculator;

import android.text.InputFilter;
import android.widget.EditText;

/**
 * Created by Akshay on 01-Sep-17.
 */

public class OnClickFunctions {



    public String onClickButtonZero(StringBuffer numberOne, StringBuffer numberTwo, String operator){
            if (operator.equals("no")) {
                numberOne.append("0");
                return numberOne.toString();
            } else {
                numberTwo.append("0");
                return numberTwo.toString();
            }

    }

    public String onClickButtonOne(StringBuffer numberOne, StringBuffer numberTwo, String operator){
        if(operator.equals("no")) {
            numberOne.append("1");
            return numberOne.toString();
        }
        else
        {
            numberTwo.append("1");
            return numberTwo.toString();
        }
    }

    public String onClickButtonTwo(StringBuffer numberOne, StringBuffer numberTwo, String operator){
        if(operator.equals("no")) {
            numberOne.append("2");
            return numberOne.toString();
        }
        else
        {
            numberTwo.append("2");
            return numberTwo.toString();
        }
    }

    public String onClickButtonThree(StringBuffer numberOne, StringBuffer numberTwo, String operator){
        if(operator.equals("no")) {
            numberOne.append("3");
            return numberOne.toString();
        }
        else
        {
            numberTwo.append("3");
            return numberTwo.toString();
        }
    }

    public String onClickButtonFour(StringBuffer numberOne, StringBuffer numberTwo, String operator){
        if(operator.equals("no")) {
            numberOne.append("4");
            return numberOne.toString();
        }
        else
        {
            numberTwo.append("4");
            return numberTwo.toString();
        }
    }

    public String onClickButtonFive(StringBuffer numberOne, StringBuffer numberTwo, String operator){
        if(operator.equals("no")) {
            numberOne.append("5");
            return numberOne.toString();
        }
        else
        {
            numberTwo.append("5");
            return numberTwo.toString();
        }
    }

    public String onClickButtonSix(StringBuffer numberOne, StringBuffer numberTwo, String operator){
        if(operator.equals("no")) {
            numberOne.append("6");
            return numberOne.toString();
        }
        else
        {
            numberTwo.append("6");
            return numberTwo.toString();
        }
    }

    public String onClickButtonSeven(StringBuffer numberOne, StringBuffer numberTwo, String operator){
        if(operator.equals("no")) {
            numberOne.append("7");
            return numberOne.toString();
        }
        else
        {
            numberTwo.append("7");
            return numberTwo.toString();
        }
    }

    public String onClickButtonEight(StringBuffer numberOne, StringBuffer numberTwo, String operator){
        if(operator.equals("no")) {
            numberOne.append("8");
            return numberOne.toString();
        }
        else
        {
            numberTwo.append("8");
            return numberTwo.toString();
        }
    }

    public String onClickButtonNine(StringBuffer numberOne, StringBuffer numberTwo, String operator){
        if(operator.equals("no")) {
            numberOne.append("9");
            return numberOne.toString();
        }
        else
        {
            numberTwo.append("9");
            return numberTwo.toString();
        }
    }

    public String onClickButtonDot(StringBuffer numberOne, StringBuffer numberTwo, String operator) {
        if (operator.equals("no")) {
            numberOne.append(".");
            return numberOne.toString();
        } else {
            numberTwo.append(".");
            return numberTwo.toString();
        }
    }

        public String onClickButtonPlus(String operator)
    {
        operator = "+";
        return operator;
    }

    public String onClickButtonMinus(String operator)
    {
            operator = "-";
            return operator;
    }

    public String onClickButtonMultiply(String operator)
    {
        operator = "*";
        return operator;
    }

    public String onClickButtonDivide(String operator)
    {
        operator = "/";
        return operator;
    }

    public EditText changeMaxLimit(Integer limit, EditText resultBar)
    {
        InputFilter[] inputFilter = new InputFilter[1];
        inputFilter[0] = new InputFilter.LengthFilter(limit);
        resultBar.setFilters(inputFilter);
        return resultBar;
    }


}
