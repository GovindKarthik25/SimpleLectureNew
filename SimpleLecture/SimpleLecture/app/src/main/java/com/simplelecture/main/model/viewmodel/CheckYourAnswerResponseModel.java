package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 10/16/2016.
 */

public class CheckYourAnswerResponseModel implements Serializable {

    private String QuestionId;
    private String QuestionName;
    private String Name;
    private String Answer1;
    private String Answer2;
    private String Answer3;
    private String Answer4;
    private String CorrectAnswer;
    private String QuizAnswer;

    public CheckYourAnswerResponseModel() {
    }

    public CheckYourAnswerResponseModel(String questionId, String questionName, String name, String answer1, String answer2, String answer3, String answer4, String correctAnswer, String quizAnswer) {
        QuestionId = questionId;
        QuestionName = questionName;
        Name = name;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        Answer4 = answer4;
        CorrectAnswer = correctAnswer;
        QuizAnswer = quizAnswer;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getQuestionName() {
        return QuestionName;
    }

    public void setQuestionName(String questionName) {
        QuestionName = questionName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getQuizAnswer() {
        return QuizAnswer;
    }

    public void setQuizAnswer(String quizAnswer) {
        QuizAnswer = quizAnswer;
    }

    @Override
    public String toString() {
        return "CheckYourAnswerResponseModel{" +
                "QuestionId='" + QuestionId + '\'' +
                ", QuestionName='" + QuestionName + '\'' +
                ", Name='" + Name + '\'' +
                ", Answer1='" + Answer1 + '\'' +
                ", Answer2='" + Answer2 + '\'' +
                ", Answer3='" + Answer3 + '\'' +
                ", Answer4='" + Answer4 + '\'' +
                ", CorrectAnswer='" + CorrectAnswer + '\'' +
                ", QuizAnswer='" + QuizAnswer + '\'' +
                '}';
    }
}
