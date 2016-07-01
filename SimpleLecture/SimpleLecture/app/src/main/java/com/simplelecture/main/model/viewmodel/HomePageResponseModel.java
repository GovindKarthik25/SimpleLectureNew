package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 6/28/2016.
 */
public class HomePageResponseModel implements Serializable {

    private int MyCoursesCount;

    private List<HomeBannersModel> bannersLst;
    private List<CourseCombos> courseCombosLst;
    private List<HomeCoursesModel> coursesLst;
    private List<HomePopularCoursesModel> homePopularCoursesModelLst;
    private List<HomeTestimonialsModel> homeTestimonialsModelLst;



    public HomePageResponseModel() {
    }

    public HomePageResponseModel(int myCoursesCount, List<HomeBannersModel> bannersLst, List<CourseCombos> courseCombosLst, List<HomeCoursesModel> coursesLst, List<HomePopularCoursesModel> homePopularCoursesModelLst, List<HomeTestimonialsModel> homeTestimonialsModelLst) {
        MyCoursesCount = myCoursesCount;
        this.bannersLst = bannersLst;
        this.courseCombosLst = courseCombosLst;
        this.coursesLst = coursesLst;
        this.homePopularCoursesModelLst = homePopularCoursesModelLst;
        this.homeTestimonialsModelLst = homeTestimonialsModelLst;
    }

    public List<HomeBannersModel> getBannersLst() {
        return bannersLst;
    }

    public void setBannersLst(List<HomeBannersModel> bannersLst) {
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

    public List<HomeCoursesModel> getCoursesLst() {
        return coursesLst;
    }

    public void setCoursesLst(List<HomeCoursesModel> coursesLst) {
        this.coursesLst = coursesLst;
    }

    public List<HomePopularCoursesModel> getPopularCoursesLst() {
        return homePopularCoursesModelLst;
    }

    public void setPopularCoursesLst(List<HomePopularCoursesModel> homePopularCoursesModelLst) {
        this.homePopularCoursesModelLst = homePopularCoursesModelLst;
    }

    public List<HomeTestimonialsModel> getHomeTestimonialsModelLst() {
        return homeTestimonialsModelLst;
    }

    public void setHomeTestimonialsModelLst(List<HomeTestimonialsModel> homeTestimonialsModelLst) {
        this.homeTestimonialsModelLst = homeTestimonialsModelLst;
    }

    @Override
    public String toString() {
        return "HomePageResponseModel{" +
                "MyCoursesCount=" + MyCoursesCount +
                ", bannersLst=" + bannersLst +
                ", courseCombosLst=" + courseCombosLst +
                ", coursesLst=" + coursesLst +
                ", homePopularCoursesModelLst=" + homePopularCoursesModelLst +
                ", homeTestimonialsModelLst=" + homeTestimonialsModelLst +
                '}';
    }
}
