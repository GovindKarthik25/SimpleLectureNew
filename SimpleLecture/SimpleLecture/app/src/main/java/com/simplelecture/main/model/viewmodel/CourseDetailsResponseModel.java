package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 2/16/2016.
 */
public class CourseDetailsResponseModel implements Serializable {

    private String cId;
    private String CoursePrice;
    private String cName;
    private String cDesc;
    private String cBenefits;
    private String cIcon;
    private String videoImage;
    private String videoUrl;
    private String cComboName;
    private boolean isCombo;
    private String courseComboNames;
    private List<ChaptersResponseModel> chaptersResponseModel;
    private List<CourseCombos> courseCombos;
    private List<courseFeatures> courseFeature;
    private List<CourseFaqs> courseFaqs;
    private List<CourseMaterials> courseMaterials;
    private List<CourseMonths> courseMonths;

    public CourseDetailsResponseModel() {
    }

    public CourseDetailsResponseModel(String cId, String coursePrice, String cName, String cDesc, String cBenefits, String cIcon, String videoImage, String videoUrl, String cComboName, boolean isCombo, String courseComboNames, List<ChaptersResponseModel> chaptersResponseModel, List<CourseCombos> courseCombos, List<courseFeatures> courseFeature, List<CourseFaqs> courseFaqs, List<CourseMaterials> courseMaterials, List<CourseMonths> courseMonths) {
        this.cId = cId;
        CoursePrice = coursePrice;
        this.cName = cName;
        this.cDesc = cDesc;
        this.cBenefits = cBenefits;
        this.cIcon = cIcon;
        this.videoImage = videoImage;
        this.videoUrl = videoUrl;
        this.cComboName = cComboName;
        this.isCombo = isCombo;
        this.courseComboNames = courseComboNames;
        this.chaptersResponseModel = chaptersResponseModel;
        this.courseCombos = courseCombos;
        this.courseFeature = courseFeature;
        this.courseFaqs = courseFaqs;
        this.courseMaterials = courseMaterials;
        this.courseMonths = courseMonths;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getCoursePrice() {
        return CoursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        CoursePrice = coursePrice;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcDesc() {
        return cDesc;
    }

    public void setcDesc(String cDesc) {
        this.cDesc = cDesc;
    }

    public String getcBenefits() {
        return cBenefits;
    }

    public void setcBenefits(String cBenefits) {
        this.cBenefits = cBenefits;
    }

    public String getcIcon() {
        return cIcon;
    }

    public void setcIcon(String cIcon) {
        this.cIcon = cIcon;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getcComboName() {
        return cComboName;
    }

    public void setcComboName(String cComboName) {
        this.cComboName = cComboName;
    }

    public boolean isCombo() {
        return isCombo;
    }

    public void setCombo(boolean combo) {
        isCombo = combo;
    }

    public String getCourseComboNames() {
        return courseComboNames;
    }

    public void setCourseComboNames(String courseComboNames) {
        this.courseComboNames = courseComboNames;
    }

    public List<ChaptersResponseModel> getChaptersResponseModel() {
        return chaptersResponseModel;
    }

    public void setChaptersResponseModel(List<ChaptersResponseModel> chaptersResponseModel) {
        this.chaptersResponseModel = chaptersResponseModel;
    }

    public List<CourseCombos> getCourseCombos() {
        return courseCombos;
    }

    public void setCourseCombos(List<CourseCombos> courseCombos) {
        this.courseCombos = courseCombos;
    }

    public List<courseFeatures> getCourseFeature() {
        return courseFeature;
    }

    public void setCourseFeature(List<courseFeatures> courseFeature) {
        this.courseFeature = courseFeature;
    }

    public List<CourseFaqs> getCourseFaqs() {
        return courseFaqs;
    }

    public void setCourseFaqs(List<CourseFaqs> courseFaqs) {
        this.courseFaqs = courseFaqs;
    }

    public List<CourseMaterials> getCourseMaterials() {
        return courseMaterials;
    }

    public void setCourseMaterials(List<CourseMaterials> courseMaterials) {
        this.courseMaterials = courseMaterials;
    }

    public List<CourseMonths> getCourseMonths() {
        return courseMonths;
    }

    public void setCourseMonths(List<CourseMonths> courseMonths) {
        this.courseMonths = courseMonths;
    }

    @Override
    public String toString() {
        return "CourseDetailsResponseModel{" +
                "cId='" + cId + '\'' +
                ", CoursePrice='" + CoursePrice + '\'' +
                ", cName='" + cName + '\'' +
                ", cDesc='" + cDesc + '\'' +
                ", cBenefits='" + cBenefits + '\'' +
                ", cIcon='" + cIcon + '\'' +
                ", videoImage='" + videoImage + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", cComboName='" + cComboName + '\'' +
                ", isCombo=" + isCombo +
                ", courseComboNames='" + courseComboNames + '\'' +
                ", chaptersResponseModel=" + chaptersResponseModel +
                ", courseCombos=" + courseCombos +
                ", courseFeature=" + courseFeature +
                ", courseFaqs=" + courseFaqs +
                ", courseMaterials=" + courseMaterials +
                ", courseMonths=" + courseMonths +
                '}';
    }
}