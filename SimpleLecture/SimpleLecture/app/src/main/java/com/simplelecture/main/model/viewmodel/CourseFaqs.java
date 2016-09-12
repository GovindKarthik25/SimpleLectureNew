package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 7/10/2016.
 */
public class CourseFaqs implements Serializable {

    private String Id;
    private String Name;
    private List<Answer> answerModel;

    public CourseFaqs() {
    }

    public CourseFaqs(String id, String name, List<Answer> answerModel) {
        Id = id;
        Name = name;
        this.answerModel = answerModel;
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

    public List<Answer> getAnswerModel() {
        return answerModel;
    }

    public void setAnswerModel(List<Answer> answerModel) {
        this.answerModel = answerModel;
    }

    @Override
    public String toString() {
        return "CourseFaqs{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", answerModel=" + answerModel +
                '}';
    }
}
