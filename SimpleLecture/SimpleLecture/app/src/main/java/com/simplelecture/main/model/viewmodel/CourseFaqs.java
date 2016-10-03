package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 7/10/2016.
 */
public class CourseFaqs implements Serializable {

    private String Id;
    private String Name;
    private List<Answer> Answer;

    public CourseFaqs() {
    }

    public CourseFaqs(String id, List<com.simplelecture.main.model.viewmodel.Answer> answer, String name) {
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

    public List<com.simplelecture.main.model.viewmodel.Answer> getAnswer() {
        return Answer;
    }

    public void setAnswer(List<com.simplelecture.main.model.viewmodel.Answer> answer) {
        Answer = answer;
    }

    @Override
    public String toString() {
        return "CourseFaqs{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Answer=" + Answer +
                '}';
    }
}
