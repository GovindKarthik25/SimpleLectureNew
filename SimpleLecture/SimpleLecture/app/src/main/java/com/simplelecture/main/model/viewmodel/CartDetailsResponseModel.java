package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Raos on 7/31/2016.
 */
public class CartDetailsResponseModel implements Serializable {

    private List<CourseMonths> CourseMonths;
    private List<CourseListCartModel> courseCartList;
    private String TotalPrice;

    public CartDetailsResponseModel() {
    }

    public CartDetailsResponseModel(List<com.simplelecture.main.model.viewmodel.CourseMonths> courseMonths, List<CourseListCartModel> courseCartList, String totalPrice) {
        CourseMonths = courseMonths;
        this.courseCartList = courseCartList;
        TotalPrice = totalPrice;
    }

    public List<CourseListCartModel> getCourseCartList() {
        return courseCartList;
    }

    public void setCourseCartList(List<CourseListCartModel> courseCartList) {
        this.courseCartList = courseCartList;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public List<com.simplelecture.main.model.viewmodel.CourseMonths> getCourseMonths() {
        return CourseMonths;
    }

    public void setCourseMonths(List<com.simplelecture.main.model.viewmodel.CourseMonths> courseMonths) {
        CourseMonths = courseMonths;
    }

    @Override
    public String toString() {
        return "CartDetailsResponseModel{" +
                "CourseMonths=" + CourseMonths +
                ", courseCartList=" + courseCartList +
                ", TotalPrice='" + TotalPrice + '\'' +
                '}';
    }
}


