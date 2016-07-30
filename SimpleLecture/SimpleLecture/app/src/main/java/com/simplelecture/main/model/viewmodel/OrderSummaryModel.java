package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by M1032185 on 7/30/2016.
 */
public class OrderSummaryModel implements Serializable {

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
        return SubTotalPrice;
    }

    public void setSubTotal(String subTotal) {
        SubTotalPrice = subTotal;
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
    private String SubTotalPrice;
    private String PromocodeDiscountPrice;
    private String TaxInclusive;

    public String getTaxPrice() {
        return TaxPrice;
    }

    public void setTaxPrice(String taxPrice) {
        TaxPrice = taxPrice;
    }

    public String getPromocodeDiscountPrice() {
        return PromocodeDiscountPrice;
    }

    public void setPromocodeDiscountPrice(String promocodeDiscountPrice) {
        PromocodeDiscountPrice = promocodeDiscountPrice;
    }

    public String getTaxInclusive() {
        return TaxInclusive;
    }

    public void setTaxInclusive(String taxInclusive) {
        TaxInclusive = taxInclusive;
    }

    public String getSubTotalPrice() {
        return SubTotalPrice;
    }

    public void setSubTotalPrice(String subTotalPrice) {
        SubTotalPrice = subTotalPrice;
    }

    private String TaxPrice;
    private List<CourseMaterials> courseMaterials;


    public OrderSummaryModel(String CourseId, String CourseName, String Price, String Icon, String Months, String SubTotal,String PromocodeDiscountPrice,String TaxInclusive,String TaxPrice) {
        this.CourseId = CourseId;
        this.CourseName = CourseName;
        this.Price = Price;
        this.Icon = Icon;
        this.Months = Months;
        this.SubTotalPrice = SubTotal;
        this.PromocodeDiscountPrice = PromocodeDiscountPrice;
        this.TaxInclusive = TaxInclusive;
        this.TaxPrice = TaxInclusive;
    }
}