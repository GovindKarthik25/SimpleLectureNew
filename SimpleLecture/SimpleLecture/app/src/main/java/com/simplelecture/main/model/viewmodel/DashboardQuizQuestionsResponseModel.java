package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 8/29/2016.
 */
public class DashboardQuizQuestionsResponseModel implements Serializable {

    private String TestId;
    private String CourseChapterId;
    private String CourseChapterName;
    private String MaxSeconds;
    private String MaxQuestions;
    private boolean IsQuizCompleted;
    private List<Questions> questions;

    public DashboardQuizQuestionsResponseModel() {
    }

    public DashboardQuizQuestionsResponseModel(String testId, String courseChapterId, String courseChapterName, String maxSeconds, String maxQuestions, boolean isQuizCompleted, List<Questions> questions) {
        TestId = testId;
        CourseChapterId = courseChapterId;
        CourseChapterName = courseChapterName;
        MaxSeconds = maxSeconds;
        MaxQuestions = maxQuestions;
        IsQuizCompleted = isQuizCompleted;
        this.questions = questions;
    }

    public String getTestId() {
        return TestId;
    }

    public void setTestId(String testId) {
        TestId = testId;
    }

    public String getCourseChapterId() {
        return CourseChapterId;
    }

    public void setCourseChapterId(String courseChapterId) {
        CourseChapterId = courseChapterId;
    }

    public String getCourseChapterName() {
        return CourseChapterName;
    }

    public void setCourseChapterName(String courseChapterName) {
        CourseChapterName = courseChapterName;
    }

    public String getMaxSeconds() {
        return MaxSeconds;
    }

    public void setMaxSeconds(String maxSeconds) {
        MaxSeconds = maxSeconds;
    }

    public String getMaxQuestions() {
        return MaxQuestions;
    }

    public void setMaxQuestions(String maxQuestions) {
        MaxQuestions = maxQuestions;
    }

    public boolean isQuizCompleted() {
        return IsQuizCompleted;
    }

    public void setQuizCompleted(boolean quizCompleted) {
        IsQuizCompleted = quizCompleted;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "DashboardQuizQuestionsResponseModel{" +
                "TestId='" + TestId + '\'' +
                ", CourseChapterId='" + CourseChapterId + '\'' +
                ", CourseChapterName='" + CourseChapterName + '\'' +
                ", MaxSeconds='" + MaxSeconds + '\'' +
                ", MaxQuestions='" + MaxQuestions + '\'' +
                ", IsQuizCompleted=" + IsQuizCompleted +
                ", questions=" + questions +
                '}';
    }
}
