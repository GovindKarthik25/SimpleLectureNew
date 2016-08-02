package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 8/1/2016.
 */
public class OrderSummaryListModel implements Serializable {

    private String CourseId;
    private String CourseName;
    private String Price;
    private String PromocodeDiscountPrice;
    private String TaxInclusive;
    private String TaxPrice;
    private String Icon;
    private String Months;
    private String SubTotalPrice;
    private String CourseMaterialNames;
    private String CourseMaterialPrices;

    public OrderSummaryListModel() {
    }

    public OrderSummaryListModel(String courseId, String courseName, String price, String promocodeDiscountPrice, String taxInclusive, String taxPrice, String icon, String months, String subTotalPrice, String courseMaterialNames, String courseMaterialPrices) {
        CourseId = courseId;
        CourseName = courseName;
        Price = price;
        PromocodeDiscountPrice = promocodeDiscountPrice;
        TaxInclusive = taxInclusive;
        TaxPrice = taxPrice;
        Icon = icon;
        Months = months;
        SubTotalPrice = subTotalPrice;
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

    public String getTaxPrice() {
        return TaxPrice;
    }

    public void setTaxPrice(String taxPrice) {
        TaxPrice = taxPrice;
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

    public String getSubTotalPrice() {
        return SubTotalPrice;
    }

    public void setSubTotalPrice(String subTotalPrice) {
        SubTotalPrice = subTotalPrice;
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
                ", PromocodeDiscountPrice='" + PromocodeDiscountPrice + '\'' +
                ", TaxInclusive='" + TaxInclusive + '\'' +
                ", TaxPrice='" + TaxPrice + '\'' +
                ", Icon='" + Icon + '\'' +
                ", Months='" + Months + '\'' +
                ", SubTotalPrice='" + SubTotalPrice + '\'' +
                ", CourseMaterialNames='" + CourseMaterialNames + '\'' +
                ", CourseMaterialPrices='" + CourseMaterialPrices + '\'' +
                '}';
    }
}
