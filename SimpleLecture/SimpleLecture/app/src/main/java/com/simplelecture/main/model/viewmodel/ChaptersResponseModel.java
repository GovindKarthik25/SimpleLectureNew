package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 2/18/2016.
 */
public class ChaptersResponseModel implements Serializable {

    private int ccId;
    private String ccName;
    private String ccNumber;
    private List<courseTopics> courseTopics;

    public ChaptersResponseModel() {
    }

    public ChaptersResponseModel(int ccId, String ccName, String ccNumber, List<com.simplelecture.main.model.viewmodel.courseTopics> courseTopics) {
        this.ccId = ccId;
        this.ccName = ccName;
        this.ccNumber = ccNumber;
        this.courseTopics = courseTopics;
    }

    public int getCcId() {
        return ccId;
    }

    public void setCcId(int ccId) {
        this.ccId = ccId;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public List<com.simplelecture.main.model.viewmodel.courseTopics> getCourseTopics() {
        return courseTopics;
    }

    public void setCourseTopics(List<com.simplelecture.main.model.viewmodel.courseTopics> courseTopics) {
        this.courseTopics = courseTopics;
    }

    @Override
    public String toString() {
        return "ChaptersResponseModel{" +
                "ccId=" + ccId +
                ", ccName='" + ccName + '\'' +
                ", ccNumber='" + ccNumber + '\'' +
                ", courseTopics=" + courseTopics +
                '}';
    }
}
