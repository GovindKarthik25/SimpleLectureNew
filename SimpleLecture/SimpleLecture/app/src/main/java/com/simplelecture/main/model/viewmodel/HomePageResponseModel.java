package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 6/28/2016.
 */
public class HomePageResponseModel implements Serializable {

    private int MyCoursesCount;

    private List<Banners> bannersLst;
    private List<CourseCombos> courseCombosLst;
    private List<Courses> coursesLst;
    private List<PopularCourses> popularCoursesLst;
    private List<Testimonials> testimonialsLst;



    public HomePageResponseModel() {
    }

    public HomePageResponseModel(int myCoursesCount, List<Banners> bannersLst, List<CourseCombos> courseCombosLst, List<Courses> coursesLst, List<PopularCourses> popularCoursesLst, List<Testimonials> testimonialsLst) {
        MyCoursesCount = myCoursesCount;
        this.bannersLst = bannersLst;
        this.courseCombosLst = courseCombosLst;
        this.coursesLst = coursesLst;
        this.popularCoursesLst = popularCoursesLst;
        this.testimonialsLst = testimonialsLst;
    }

    public List<Banners> getBannersLst() {
        return bannersLst;
    }

    public void setBannersLst(List<Banners> bannersLst) {
        this.bannersLst = bannersLst;
    }

    public int getMyCoursesCount() {
        return MyCoursesCount;
    }

    public void setMyCoursesCount(int myCoursesCount) {
        MyCoursesCount = myCoursesCount;
    }

    public List<CourseCombos> getCourseCombosLst() {
        return courseCombosLst;
    }

    public void setCourseCombosLst(List<CourseCombos> courseCombosLst) {
        this.courseCombosLst = courseCombosLst;
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
                ", courseCombosLst=" + courseCombosLst +
                ", coursesLst=" + coursesLst +
                ", popularCoursesLst=" + popularCoursesLst +
                ", testimonialsLst=" + testimonialsLst +
                '}';
    }
}
