package com.simplelecture.main.model.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raos on 2/16/2016.
 */
public class MyCoursesResponseModel {

    List<myCourses> mycourses;

    public MyCoursesResponseModel() {
    }

    public MyCoursesResponseModel(List<myCourses> mycourses) {
        this.mycourses = mycourses;
    }

    public List<myCourses> getMycourses() {
        return mycourses;
    }

    public void setMycourses(List<myCourses> mycourses) {
        this.mycourses = mycourses;
    }

    @Override
    public String toString() {
        return "MyCoursesResponseModel{" +
                "mycourses=" + mycourses +
                '}';
    }
}
