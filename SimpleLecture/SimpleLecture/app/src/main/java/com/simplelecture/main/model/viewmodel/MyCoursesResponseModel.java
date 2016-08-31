package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 8/25/2016.
 */
public class MyCoursesResponseModel implements Serializable {

    private String CourseId;
    private String CategoryId;
    private String CourseName;
    private String CourseIcon;
    private String CompletePer;
    private String PendingDays;

    public MyCoursesResponseModel() {
    }

    public MyCoursesResponseModel(String courseId, String categoryId, String courseName, String courseIcon, String completePer, String pendingDays) {
        CourseId = courseId;
        CategoryId = categoryId;
        CourseName = courseName;
        CourseIcon = courseIcon;
        CompletePer = completePer;
        PendingDays = pendingDays;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
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

    public String getCompletePer() {
        return CompletePer;
    }

    public void setCompletePer(String completePer) {
        CompletePer = completePer;
    }

    public String getPendingDays() {
        return PendingDays;
    }

    public void setPendingDays(String pendingDays) {
        PendingDays = pendingDays;
    }

    @Override
    public String toString() {
        return "MyCoursesResponseModel{" +
                "CourseId='" + CourseId + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", CourseName='" + CourseName + '\'' +
                ", CourseIcon='" + CourseIcon + '\'' +
                ", CompletePer='" + CompletePer + '\'' +
                ", PendingDays='" + PendingDays + '\'' +
                '}';
    }
}
