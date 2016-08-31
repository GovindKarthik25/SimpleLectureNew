package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 8/30/2016.
 */
public class QuestionsAnswer implements Serializable {

    private String QuestionId;
    private String Answer1;
    private String Answer2;
    private String Answer3;
    private String Answer4;
    private boolean selected;

    public QuestionsAnswer() {
    }

    public QuestionsAnswer(String questionId, String answer1, String answer2, String answer3, String answer4, boolean selected) {
        QuestionId = questionId;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        Answer4 = answer4;
        this.selected = selected;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public void setAnswer3(String answer3) {
        Answer3 = answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public void setAnswer4(String answer4) {
        Answer4 = answer4;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "QuestionsAnswer{" +
                "QuestionId='" + QuestionId + '\'' +
                ", Answer1='" + Answer1 + '\'' +
                ", Answer2='" + Answer2 + '\'' +
                ", Answer3='" + Answer3 + '\'' +
                ", Answer4='" + Answer4 + '\'' +
                ", selected=" + selected +
                '}';
    }
}
