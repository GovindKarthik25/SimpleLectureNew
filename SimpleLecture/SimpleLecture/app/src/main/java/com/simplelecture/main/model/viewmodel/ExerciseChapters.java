package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 8/26/2016.
 */
public class ExerciseChapters implements Serializable {

    private String CourseId;
    private String CourseChapterId;
    private String CourseChapterName;
    private String CourseChapterNumber;
    private String IsFileDownloaded;

    public ExerciseChapters() {
    }

    public ExerciseChapters(String courseId, String courseChapterId, String courseChapterName, String courseChapterNumber, String isFileDownloaded) {
        CourseId = courseId;
        CourseChapterId = courseChapterId;
        CourseChapterName = courseChapterName;
        CourseChapterNumber = courseChapterNumber;
        IsFileDownloaded = isFileDownloaded;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
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

    public String getIsFileDownloaded() {
        return IsFileDownloaded;
    }

    public void setIsFileDownloaded(String isFileDownloaded) {
        IsFileDownloaded = isFileDownloaded;
    }

    @Override
    public String toString() {
        return "ExerciseChapters{" +
                "CourseId='" + CourseId + '\'' +
                ", CourseChapterId='" + CourseChapterId + '\'' +
                ", CourseChapterName='" + CourseChapterName + '\'' +
                ", CourseChapterNumber='" + CourseChapterNumber + '\'' +
                ", IsFileDownloaded='" + IsFileDownloaded + '\'' +
                '}';
    }
}
