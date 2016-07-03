package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 3/29/2016.
 */
public class SampleVideoResponseModel implements Serializable {

    private int svName;
    private String svUrl;
    private String svImage;

    public SampleVideoResponseModel() {
    }

    public SampleVideoResponseModel(int svName, String svUrl, String svImage) {
        this.svName = svName;
        this.svUrl = svUrl;
        this.svImage = svImage;
    }

    public int getSvName() {
        return svName;
    }

    public void setSvName(int svName) {
        this.svName = svName;
    }

    public String getSvUrl() {
        return svUrl;
    }

    public void setSvUrl(String svUrl) {
        this.svUrl = svUrl;
    }

    public String getSvImage() {
        return svImage;
    }

    public void setSvImage(String svImage) {
        this.svImage = svImage;
    }

    @Override
    public String toString() {
        return "SampleVideoResponseModel{" +
                "svName=" + svName +
                ", svUrl='" + svUrl + '\'' +
                ", svImage='" + svImage + '\'' +
                '}';
    }
}

