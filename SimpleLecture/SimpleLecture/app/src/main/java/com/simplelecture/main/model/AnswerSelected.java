package com.simplelecture.main.model;

import java.io.Serializable;

/**
 * Created by Raos on 8/31/2016.
 */
public class AnswerSelected implements Serializable {

    private String QuestionId;
    private String QuestionAnswer;

    public AnswerSelected() {
    }

    public AnswerSelected(String questionId, String questionAnswer) {
        QuestionId = questionId;
        QuestionAnswer = questionAnswer;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getQuestionAnswer() {
        return QuestionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        QuestionAnswer = questionAnswer;
    }

    @Override
    public String toString() {
        return "AnswerSelected{" +
                "QuestionId='" + QuestionId + '\'' +
                ", QuestionAnswer='" + QuestionAnswer + '\'' +
                '}';
    }
}
