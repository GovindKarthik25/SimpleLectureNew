package com.simplelecture.main.model.viewmodel;

import java.util.List;

/**
 * Created by M1032185 on 8/18/2016.
 */
public class DashboardResponseModel {

//    private String isSuccess;
//    private String message;

    public List<myCourses> getMyCourses() {
        return MyCourses;
    }

    public void setMyCourses(List<myCourses> myCourses) {
        MyCourses = myCourses;
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

    private List<myCourses> MyCourses;

    private List<ForumTopics> ForumTopics;

    private List<RelatedCourses> RelatedCourses;

    private String QuizAttended;

    private String QuizPending;

    private String ExerciseDownloaded;

    private String ExercisePending;

    public void DashboardResponseModel(List<myCourses> MyCourses,List<ForumTopics> ForumTopics,
                                       List<RelatedCourses> RelatedCourses,String QuizAttended,String QuizPending,String ExerciseDownloaded,String ExercisePending){

        this.MyCourses = MyCourses;
        this.ForumTopics = ForumTopics;
        this.RelatedCourses = RelatedCourses;
        this.QuizAttended = QuizAttended;
        this.QuizPending = QuizPending;
        this.ExerciseDownloaded = ExerciseDownloaded;
        this.ExercisePending = ExercisePending;

    }


}
