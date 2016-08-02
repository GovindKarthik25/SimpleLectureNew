package com.simplelecture.main.model.viewmodel;

/**
 * Created by Raos on 7/31/2016.
 */
public class CourseListCartModel {

    private String CourseId;
    private String CourseName;
    private String Price;
    private String Icon;
    private String Months;
    private String SubTotalPrice;
    private String CourseMaterialNames;
    private String CourseMaterialPrices;
   // private List<CourseMaterials> courseMaterialsList;

    public CourseListCartModel() {
    }

    public CourseListCartModel(String courseId, String courseName, String price, String icon, String months, String subTotalPrice, String courseMaterialNames, String courseMaterialPrices) {
        CourseId = courseId;
        CourseName = courseName;
        Price = price;
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
        return "CourseListCartModel{" +
                "CourseId='" + CourseId + '\'' +
                ", CourseName='" + CourseName + '\'' +
                ", Price='" + Price + '\'' +
                ", Icon='" + Icon + '\'' +
                ", Months='" + Months + '\'' +
                ", SubTotalPrice='" + SubTotalPrice + '\'' +
                ", CourseMaterialNames='" + CourseMaterialNames + '\'' +
                ", CourseMaterialPrices='" + CourseMaterialPrices + '\'' +
                '}';
    }
}
