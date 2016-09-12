package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 8/25/2016.
 */
public class DashboardMyCoursesResponseModel implements Serializable {

    private String CourseId;
    private String CourseName;
    private String CourseIcon;
    private String CourseDesc;
    private String CourseRating;
    private String TotalChapters;
    private String TotalExercises;
    private String ExerciseDownloaded;

    public DashboardMyCoursesResponseModel() {
    }

    public DashboardMyCoursesResponseModel(String courseId, String courseName, String courseIcon, String courseDesc, String courseRating, String totalChapters, String totalExercises, String exerciseDownloaded) {
        CourseId = courseId;
        CourseName = courseName;
        CourseIcon = courseIcon;
        CourseDesc = courseDesc;
        CourseRating = courseRating;
        TotalChapters = totalChapters;
        TotalExercises = totalExercises;
        ExerciseDownloaded = exerciseDownloaded;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseIcon() {
        return CourseIcon;
    }

    public void setCourseIcon(String courseIcon) {
        CourseIcon = courseIcon;
    }

    public String getCourseDesc() {
        return CourseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        CourseDesc = courseDesc;
    }

    public String getCourseRating() {
        return CourseRating;
    }

    public void setCourseRating(String courseRating) {
        CourseRating = courseRating;
    }

    public String getTotalChapters() {
        return TotalChapters;
    }

    public void setTotalChapters(String totalChapters) {
        TotalChapters = totalChapters;
    }

    public String getTotalExercises() {
        return TotalExercises;
    }

    public void setTotalExercises(String totalExercises) {
        TotalExercises = totalExercises;
    }

    public String getExerciseDownloaded() {
        return ExerciseDownloaded;
    }

    public void setExerciseDownloaded(String exerciseDownloaded) {
        ExerciseDownloaded = exerciseDownloaded;
    }

    @Override
    public String toString() {
        return "DashboardMyCoursesResponseModel{" +
                "CourseId='" + CourseId + '\'' +
                ", CourseName='" + CourseName + '\'' +
                ", CourseIcon='" + CourseIcon + '\'' +
                ", CourseDesc='" + CourseDesc + '\'' +
                ", CourseRating='" + CourseRating + '\'' +
                ", TotalChapters='" + TotalChapters + '\'' +
                ", TotalExercises='" + TotalExercises + '\'' +
                ", ExerciseDownloaded='" + ExerciseDownloaded + '\'' +
                '}';
    }
}
