package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 6/28/2016.
 */
public class HomePageResponseModel implements Serializable {

    private int MyCoursesCount;

    private List<Banners> bannersLst;
    private List<Courses> coursesLst;
    private List<PopularCourses> popularCoursesLst;
    private List<Testimonials> testimonialsLst;



    public HomePageResponseModel() {
    }

    public HomePageResponseModel(int myCoursesCount, List<Banners> bannersLst, List<Courses> coursesLst, List<Testimonials> testimonialsLst, List<PopularCourses> popularCoursesLst) {
        MyCoursesCount = myCoursesCount;
        this.bannersLst = bannersLst;
        this.coursesLst = coursesLst;
        this.testimonialsLst = testimonialsLst;
        this.popularCoursesLst = popularCoursesLst;
    }

    public int getMyCoursesCount() {
        return MyCoursesCount;
    }

    public void setMyCoursesCount(int myCoursesCount) {
        MyCoursesCount = myCoursesCount;
    }

    public List<Banners> getBannersLst() {
        return bannersLst;
    }

    public void setBannersLst(List<Banners> bannersLst) {
        this.bannersLst = bannersLst;
    }

    public List<Courses> getCoursesLst() {
        return coursesLst;
    }

    public void setCoursesLst(List<Courses> coursesLst) {
        this.coursesLst = coursesLst;
    }

    public List<PopularCourses> getPopularCoursesLst() {
        return popularCoursesLst;
    }

    public void setPopularCoursesLst(List<PopularCourses> popularCoursesLst) {
        this.popularCoursesLst = popularCoursesLst;
    }

    public List<Testimonials> getTestimonialsLst() {
        return testimonialsLst;
    }

    public void setTestimonialsLst(List<Testimonials> testimonialsLst) {
        this.testimonialsLst = testimonialsLst;
    }

    @Override
    public String toString() {
        return "HomePageResponseModel{" +
                "MyCoursesCount=" + MyCoursesCount +
                ", bannersLst=" + bannersLst +
                ", coursesLst=" + coursesLst +
                ", popularCoursesLst=" + popularCoursesLst +
                ", testimonialsLst=" + testimonialsLst +
                '}';
    }
}
