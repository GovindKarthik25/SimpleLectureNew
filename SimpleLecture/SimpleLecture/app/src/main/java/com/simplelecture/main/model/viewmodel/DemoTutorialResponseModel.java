package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 3/29/2016.
 */
public class DemoTutorialResponseModel implements Serializable {

    private int cId;
    private String cName;
    private String cIcon;

    public DemoTutorialResponseModel() {
    }

    public DemoTutorialResponseModel(int cId, String cIcon, String cName) {
        this.cId = cId;
        this.cIcon = cIcon;
        this.cName = cName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcIcon() {
        return cIcon;
    }

    public void setcIcon(String cIcon) {
        this.cIcon = cIcon;
    }

    @Override
    public String toString() {
        return "DemoTutorialResponseModel{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", cIcon='" + cIcon + '\'' +
                '}';
    }
}
