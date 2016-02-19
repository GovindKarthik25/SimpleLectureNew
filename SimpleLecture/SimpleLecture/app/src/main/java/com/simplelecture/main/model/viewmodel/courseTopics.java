package com.simplelecture.main.model.viewmodel;

/**
 * Created by Raos on 2/18/2016.
 */
public class courseTopics {

    private int ctId;
    private String ctName;
    private String ctNumber;
    private String ctVideo;

    public courseTopics() {
    }

    public courseTopics(int ctId, String ctNumber, String ctName, String ctVideo) {
        this.ctId = ctId;
        this.ctNumber = ctNumber;
        this.ctName = ctName;
        this.ctVideo = ctVideo;
    }

    public int getCtId() {
        return ctId;
    }

    public void setCtId(int ctId) {
        this.ctId = ctId;
    }

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
    }

    public String getCtNumber() {
        return ctNumber;
    }

    public void setCtNumber(String ctNumber) {
        this.ctNumber = ctNumber;
    }

    public String getCtVideo() {
        return ctVideo;
    }

    public void setCtVideo(String ctVideo) {
        this.ctVideo = ctVideo;
    }

    @Override
    public String toString() {
        return "courseTopics{" +
                "ctId=" + ctId +
                ", ctName='" + ctName + '\'' +
                ", ctNumber='" + ctNumber + '\'' +
                ", ctVideo='" + ctVideo + '\'' +
                '}';
    }
}
