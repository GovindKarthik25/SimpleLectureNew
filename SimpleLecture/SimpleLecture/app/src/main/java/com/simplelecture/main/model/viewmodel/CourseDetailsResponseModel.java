package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 2/16/2016.
 */
public class CourseDetailsResponseModel implements Serializable {

    private String cId;
    private String cName;
    private String cDesc;
    private String cBenefits;
    private String cIcon;
    private String videoId;
    private boolean isCombo;
    private List<courseFeatures> courseFeature;
    private List<CourseCombos> courseCombos;
    private List<ChaptersResponseModel> chaptersResponseModel;

    public CourseDetailsResponseModel() {
    }

    public CourseDetailsResponseModel(String cId, String cName, String cDesc, String cBenefits, String cIcon, String videoId, boolean isCombo, List<courseFeatures> courseFeature, List<CourseCombos> courseCombos, List<ChaptersResponseModel> chaptersResponseModel) {
        this.cId = cId;
        this.cName = cName;
        this.cDesc = cDesc;
        this.cBenefits = cBenefits;
        this.cIcon = cIcon;
        this.videoId = videoId;
        this.isCombo = isCombo;
        this.courseFeature = courseFeature;
        this.courseCombos = courseCombos;
        this.chaptersResponseModel = chaptersResponseModel;
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

    public boolean isCombo() {
        return isCombo;
    }

    public void setIsCombo(boolean isCombo) {
        this.isCombo = isCombo;
    }

    public List<courseFeatures> getCourseFeature() {
        return courseFeature;
    }

    public void setCourseFeature(List<courseFeatures> courseFeature) {
        this.courseFeature = courseFeature;
    }

    public List<CourseCombos> getCourseCombos() {
        return courseCombos;
    }

    public void setCourseCombos(List<CourseCombos> courseCombos) {
        this.courseCombos = courseCombos;
    }

    public List<ChaptersResponseModel> getChaptersResponseModel() {
        return chaptersResponseModel;
    }

    public void setChaptersResponseModel(List<ChaptersResponseModel> chaptersResponseModel) {
        this.chaptersResponseModel = chaptersResponseModel;
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
                ", isCombo=" + isCombo +
                ", courseFeature=" + courseFeature +
                ", courseCombos=" + courseCombos +
                ", chaptersResponseModel=" + chaptersResponseModel +
                '}';
    }
}
