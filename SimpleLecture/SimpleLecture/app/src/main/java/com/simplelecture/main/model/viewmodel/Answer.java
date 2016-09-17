package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 9/5/2016.
 */
public class Answer implements Serializable{
    private String Id;
    private String Name;
    private String Answer;

    public Answer() {
    }

    public Answer(String id, String answer, String name) {
        Id = id;
        Answer = answer;
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Answer='" + Answer + '\'' +
                '}';
    }
}
