package com.simplelecture.main.model.viewmodel;

import java.util.List;

/**
 * Created by Raos on 2/16/2016.
 */
public class CourseDetailsResponseModel  {

    private String cId;
    private String cName;
    private String cDesc;
    private String cBenefits;
    private String cIcon;
    private String videoId;
    private String isCombo;
    List<courseFeatures> courseFeature;
    private String courseCombos;

    public CourseDetailsResponseModel() {
    }

    public CourseDetailsResponseModel(String cId, String cName, String cDesc, String cBenefits, String cIcon, String videoId, String isCombo, List<courseFeatures> courseFeature, String courseCombos) {
        this.cId = cId;
        this.cName = cName;
        this.cDesc = cDesc;
        this.cBenefits = cBenefits;
        this.cIcon = cIcon;
        this.videoId = videoId;
        this.isCombo = isCombo;
        this.courseFeature = courseFeature;
        this.courseCombos = courseCombos;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getIsCombo() {
        return isCombo;
    }

    public void setIsCombo(String isCombo) {
        this.isCombo = isCombo;
    }

    public List<courseFeatures> getCourseFeature() {
        return courseFeature;
    }

    public void setCourseFeature(List<courseFeatures> courseFeature) {
        this.courseFeature = courseFeature;
    }

    public String getCourseCombos() {
        return courseCombos;
    }

    public void setCourseCombos(String courseCombos) {
        this.courseCombos = courseCombos;
    }

    @Override
    public String toString() {
        return "CourseDetailsResponseModel{" +
                "cId='" + cId + '\'' +
                ", cName='" + cName + '\'' +
                ", cDesc='" + cDesc + '\'' +
                ", cBenefits='" + cBenefits + '\'' +
                ", cIcon='" + cIcon + '\'' +
                ", videoId='" + videoId + '\'' +
                ", isCombo='" + isCombo + '\'' +
                ", courseFeature=" + courseFeature +
                ", courseCombos='" + courseCombos + '\'' +
                '}';
    }
}
