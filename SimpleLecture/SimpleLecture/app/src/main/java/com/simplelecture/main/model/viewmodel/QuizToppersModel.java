package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 8/30/2016.
 */
public class QuizToppersModel implements Serializable {

    private String Name;
    private String Seconds;
    private String CorrectAnswers;

    public QuizToppersModel() {
    }

    public QuizToppersModel(String name, String seconds, String correctAnswers) {
        Name = name;
        Seconds = seconds;
        CorrectAnswers = correctAnswers;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCorrectAnswers() {
        return CorrectAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        CorrectAnswers = correctAnswers;
    }

    public String getSeconds() {
        return Seconds;
    }

    public void setSeconds(String seconds) {
        Seconds = seconds;
    }

    @Override
    public String toString() {
        return "QuizToppersModel{" +
                "Name='" + Name + '\'' +
                ", Seconds='" + Seconds + '\'' +
                ", CorrectAnswers='" + CorrectAnswers + '\'' +
                '}';
    }
}
