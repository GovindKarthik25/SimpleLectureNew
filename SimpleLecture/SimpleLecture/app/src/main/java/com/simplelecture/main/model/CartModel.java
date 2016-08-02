package com.simplelecture.main.model;

/**
 * Created by Raos on 8/1/2016.
 */
public class CartModel {

    private String CourseID;
    private String Months;
    private String CourseMaterials;

    public CartModel() {
    }

    public CartModel(String courseID, String months, String courseMaterials) {
        CourseID = courseID;
        Months = months;
        CourseMaterials = courseMaterials;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public String getMonths() {
        return Months;
    }

    public void setMonths(String months) {
        Months = months;
    }

    public String getCourseMaterials() {
        return CourseMaterials;
    }

    public void setCourseMaterials(String courseMaterials) {
        CourseMaterials = courseMaterials;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "CourseID='" + CourseID + '\'' +
                ", Months='" + Months + '\'' +
                ", CourseMaterials='" + CourseMaterials + '\'' +
                '}';
    }
}
