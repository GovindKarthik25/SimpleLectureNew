package com.simplelecture.main.model.viewmodel;

import java.util.List;

/**
 * Created by M1032185 on 8/18/2016.
 */
public class DashboardResponseModel {

    private List<MyCoursesResponseModel> myCoursesResponseModel;
    private List<ForumTopics> ForumTopics;
    private List<RelatedCourses> RelatedCourses;
    private String QuizAttended;
    private String QuizPending;
    private String ExerciseDownloaded;
    private String ExercisePending;

    public DashboardResponseModel() {
    }

    public DashboardResponseModel(List<MyCoursesResponseModel> myCoursesResponseModel, List<com.simplelecture.main.model.viewmodel.ForumTopics> forumTopics, List<com.simplelecture.main.model.viewmodel.RelatedCourses> relatedCourses, String quizAttended, String quizPending, String exerciseDownloaded, String exercisePending) {
        this.myCoursesResponseModel = myCoursesResponseModel;
        ForumTopics = forumTopics;
        RelatedCourses = relatedCourses;
        QuizAttended = quizAttended;
        QuizPending = quizPending;
        ExerciseDownloaded = exerciseDownloaded;
        ExercisePending = exercisePending;
    }

    public List<MyCoursesResponseModel> getMyCoursesResponseModel() {
        return myCoursesResponseModel;
    }

    public void setMyCoursesResponseModel(List<MyCoursesResponseModel> myCoursesResponseModel) {
        this.myCoursesResponseModel = myCoursesResponseModel;
    }

    public List<com.simplelecture.main.model.viewmodel.ForumTopics> getForumTopics() {
        return ForumTopics;
    }

    public void setForumTopics(List<com.simplelecture.main.model.viewmodel.ForumTopics> forumTopics) {
        ForumTopics = forumTopics;
    }

    public List<com.simplelecture.main.model.viewmodel.RelatedCourses> getRelatedCourses() {
        return RelatedCourses;
    }

    public void setRelatedCourses(List<com.simplelecture.main.model.viewmodel.RelatedCourses> relatedCourses) {
        RelatedCourses = relatedCourses;
    }

    public String getQuizAttended() {
        return QuizAttended;
    }

    public void setQuizAttended(String quizAttended) {
        QuizAttended = quizAttended;
    }

    public String getQuizPending() {
        return QuizPending;
    }

    public void setQuizPending(String quizPending) {
        QuizPending = quizPending;
    }

    public String getExerciseDownloaded() {
        return ExerciseDownloaded;
    }

    public void setExerciseDownloaded(String exerciseDownloaded) {
        ExerciseDownloaded = exerciseDownloaded;
    }

    public String getExercisePending() {
        return ExercisePending;
    }

    public void setExercisePending(String exercisePending) {
        ExercisePending = exercisePending;
    }

    @Override
    public String toString() {
        return "DashboardResponseModel{" +
                "myCoursesResponseModel=" + myCoursesResponseModel +
                ", ForumTopics=" + ForumTopics +
                ", RelatedCourses=" + RelatedCourses +
                ", QuizAttended='" + QuizAttended + '\'' +
                ", QuizPending='" + QuizPending + '\'' +
                ", ExerciseDownloaded='" + ExerciseDownloaded + '\'' +
                ", ExercisePending='" + ExercisePending + '\'' +
                '}';
    }
}
