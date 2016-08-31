package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 8/26/2016.
 */
public class ExerciseResponseModel implements Serializable {

    private String CourseId;
    private String CourseName;
    private String CourseIcon;
    private String DownloadedCount;
    private String RemainingCount;
    private List<ExerciseChapters> exerciseChapters;

    public ExerciseResponseModel() {
    }

    public ExerciseResponseModel(String courseId, String courseName, String courseIcon, String downloadedCount, String remainingCount, List<ExerciseChapters> exerciseChapters) {
        CourseId = courseId;
        CourseName = courseName;
        CourseIcon = courseIcon;
        DownloadedCount = downloadedCount;
        RemainingCount = remainingCount;
        this.exerciseChapters = exerciseChapters;
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

    public String getDownloadedCount() {
        return DownloadedCount;
    }

    public void setDownloadedCount(String downloadedCount) {
        DownloadedCount = downloadedCount;
    }

    public String getRemainingCount() {
        return RemainingCount;
    }

    public void setRemainingCount(String remainingCount) {
        RemainingCount = remainingCount;
    }

    public List<ExerciseChapters> getExerciseChapters() {
        return exerciseChapters;
    }

    public void setExerciseChapters(List<ExerciseChapters> exerciseChapters) {
        this.exerciseChapters = exerciseChapters;
    }

    @Override
    public String toString() {
        return "ExerciseResponseModel{" +
                "CourseId='" + CourseId + '\'' +
                ", CourseName='" + CourseName + '\'' +
                ", CourseIcon='" + CourseIcon + '\'' +
                ", DownloadedCount='" + DownloadedCount + '\'' +
                ", RemainingCount='" + RemainingCount + '\'' +
                ", exerciseChapters=" + exerciseChapters +
                '}';
    }
}
