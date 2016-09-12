package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 8/1/2016.
 */
public class OrderSummaryListModel implements Serializable {

    private String CourseId;
    private String CourseName;
    private String Price;
    private String Icon;
    private String Months;
    private String TotalCoursePrice;
    private String CourseMaterialNames;
    private String CourseMaterialPrices;

    public OrderSummaryListModel() {
    }

    public OrderSummaryListModel(String courseId, String courseName, String price, String icon, String months, String totalCoursePrice, String courseMaterialNames, String courseMaterialPrices) {
        CourseId = courseId;
        CourseName = courseName;
        Price = price;
        Icon = icon;
        Months = months;
        TotalCoursePrice = totalCoursePrice;
        CourseMaterialNames = courseMaterialNames;
        CourseMaterialPrices = courseMaterialPrices;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getMonths() {
        return Months;
    }

    public void setMonths(String months) {
        Months = months;
    }

    public String getTotalCoursePrice() {
        return TotalCoursePrice;
    }

    public void setTotalCoursePrice(String totalCoursePrice) {
        TotalCoursePrice = totalCoursePrice;
    }

    public String getCourseMaterialNames() {
        return CourseMaterialNames;
    }

    public void setCourseMaterialNames(String courseMaterialNames) {
        CourseMaterialNames = courseMaterialNames;
    }

    public String getCourseMaterialPrices() {
        return CourseMaterialPrices;
    }

    public void setCourseMaterialPrices(String courseMaterialPrices) {
        CourseMaterialPrices = courseMaterialPrices;
    }

    @Override
    public String toString() {
        return "OrderSummaryListModel{" +
                "CourseId='" + CourseId + '\'' +
                ", CourseName='" + CourseName + '\'' +
                ", Price='" + Price + '\'' +
                ", Icon='" + Icon + '\'' +
                ", Months='" + Months + '\'' +
                ", TotalCoursePrice='" + TotalCoursePrice + '\'' +
                ", CourseMaterialNames='" + CourseMaterialNames + '\'' +
                ", CourseMaterialPrices='" + CourseMaterialPrices + '\'' +
                '}';
    }
}
