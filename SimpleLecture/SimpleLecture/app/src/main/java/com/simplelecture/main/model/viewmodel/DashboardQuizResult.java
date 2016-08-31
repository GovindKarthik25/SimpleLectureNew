package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 8/30/2016.
 */
public class DashboardQuizResult implements Serializable{

    private String TestId;
    private String QuizSeconds;
    private String QuizCorrectAnswer;
    private String MaxQuestions;
    private List<QuizToppersModel> quizToppers;

    public DashboardQuizResult() {
    }

    public DashboardQuizResult(String testId, String quizSeconds, String quizCorrectAnswer, String maxQuestions, List<QuizToppersModel> quizToppers) {
        TestId = testId;
        QuizSeconds = quizSeconds;
        QuizCorrectAnswer = quizCorrectAnswer;
        MaxQuestions = maxQuestions;
        this.quizToppers = quizToppers;
    }

    public String getTestId() {
        return TestId;
    }

    public void setTestId(String testId) {
        TestId = testId;
    }

    public String getQuizSeconds() {
        return QuizSeconds;
    }

    public void setQuizSeconds(String quizSeconds) {
        QuizSeconds = quizSeconds;
    }

    public String getQuizCorrectAnswer() {
        return QuizCorrectAnswer;
    }

    public void setQuizCorrectAnswer(String quizCorrectAnswer) {
        QuizCorrectAnswer = quizCorrectAnswer;
    }

    public String getMaxQuestions() {
        return MaxQuestions;
    }

    public void setMaxQuestions(String maxQuestions) {
        MaxQuestions = maxQuestions;
    }

    public List<QuizToppersModel> getQuizToppers() {
        return quizToppers;
    }

    public void setQuizToppers(List<QuizToppersModel> quizToppers) {
        this.quizToppers = quizToppers;
    }

    @Override
    public String toString() {
        return "DashboardQuizResult{" +
                "TestId='" + TestId + '\'' +
                ", QuizSeconds='" + QuizSeconds + '\'' +
                ", QuizCorrectAnswer='" + QuizCorrectAnswer + '\'' +
                ", MaxQuestions='" + MaxQuestions + '\'' +
                ", quizToppers=" + quizToppers +
                '}';
    }
}
