package com.simplelecture.main.model.viewmodel;

import java.util.List;

/**
 * Created by Raos on 2/16/2016.
 */
public class MyCoursesResponseModel {

    List<MyCourses> mycourses;

    public MyCoursesResponseModel() {
    }

    public MyCoursesResponseModel(List<MyCourses> mycourses) {
        this.mycourses = mycourses;
    }

    public List<MyCourses> getMycourses() {
        return mycourses;
    }

    public void setMycourses(List<MyCourses> mycourses) {
        this.mycourses = mycourses;
    }

    @Override
    public String toString() {
        return "MyCoursesResponseModel{" +
                "mycourses=" + mycourses +
                '}';
    }
}
