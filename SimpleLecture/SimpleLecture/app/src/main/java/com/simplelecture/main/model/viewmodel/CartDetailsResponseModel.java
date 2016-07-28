package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by M1032185 on 7/28/2016.
 */
public class CartDetailsResponseModel implements Serializable {

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

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }

    public List<CourseMaterials> getCourseMaterials() {
        return courseMaterials;
    }

    public void setCourseMaterials(List<CourseMaterials> courseMaterials) {
        this.courseMaterials = courseMaterials;
    }

    private String CourseId;
    private String CourseName;
    private String Price;
    private String Icon;
    private String Months;
    private String SubTotal;
    private List<CourseMaterials> courseMaterials;


    public CartDetailsResponseModel(String CourseId,String CourseName,String Price,String Icon,String Months,String SubTotal){
        this.CourseId = CourseId;
        this.CourseName = CourseName;
        this.Price = Price;
        this.Icon = Icon;
        this.Months = Months;
        this.SubTotal = SubTotal;
    }
}
