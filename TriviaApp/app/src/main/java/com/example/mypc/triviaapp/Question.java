package com.example.mypc.triviaapp;

import java.io.Serializable;
import java.util.ArrayList;


public class Question implements Serializable{

    Integer id;
    String questionText;
    String imageURL;
    ArrayList<String> choices;
    Integer answer;

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", choices=" + choices.toString() +
                ", answer=" + answer +
                '}';
    }
}
