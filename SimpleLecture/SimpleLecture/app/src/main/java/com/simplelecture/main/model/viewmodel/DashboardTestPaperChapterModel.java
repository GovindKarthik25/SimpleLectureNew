package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 8/28/2016.
 */
public class DashboardTestPaperChapterModel implements Serializable {

    private String CourseChapterId;
    private String CourseChapterName;
    private String CourseChapterNumber;

    public DashboardTestPaperChapterModel(String courseChapterId, String courseChapterName, String courseChapterNumber) {
        CourseChapterId = courseChapterId;
        CourseChapterName = courseChapterName;
        CourseChapterNumber = courseChapterNumber;
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

    public String getCourseChapterNumber() {
        return CourseChapterNumber;
    }

    public void setCourseChapterNumber(String courseChapterNumber) {
        CourseChapterNumber = courseChapterNumber;
    }

    @Override
    public String toString() {
        return "DashboardTestPaperChapterModel{" +
                "CourseChapterId='" + CourseChapterId + '\'' +
                ", CourseChapterName='" + CourseChapterName + '\'' +
                ", CourseChapterNumber='" + CourseChapterNumber + '\'' +
                '}';
    }
}
