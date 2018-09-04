package com.example.mypc.triviaapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by My Pc on 03-10-2017.
 */

public class DataUtilsClass {

    public static ArrayList<Question> parsePersons(String in) throws JSONException {

        ArrayList<Question> iList = new ArrayList<Question>();
        try {
            JSONObject root = new JSONObject(in);

            JSONArray jsonArray = root.getJSONArray("questions");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Question questions = new Question();

                questions.setId(Integer.parseInt(jsonObject.getString("id")));
                questions.setQuestionText(jsonObject.getString("text"));
                if(jsonObject.has("image") && !jsonObject.isNull("image"))
                    questions.setImageURL(jsonObject.getString("image"));

                JSONObject choicesJSON = jsonObject.getJSONObject("choices");
                ArrayList<String> tempSArray = new ArrayList<String>();

                JSONArray choicesJSONArray = choicesJSON.getJSONArray("choice");
                for (int j =0;j<choicesJSONArray.length();j++) {
                    tempSArray.add(choicesJSONArray.getString(j));
                }

                questions.setAnswer(Integer.parseInt(choicesJSON.getString("answer")));
                questions.setChoices(tempSArray);
                iList.add(questions);
            }
        } catch (Exception e) {

        }

        return iList;

    }

}
