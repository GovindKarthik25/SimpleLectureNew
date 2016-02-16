package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 2/16/2016.
 */
public class courseFeatures implements Serializable {

    private String cfId;
    private String cfName;
    private String cfIcon;

    public courseFeatures() {
    }

    public courseFeatures(String cfId, String cfName, String cfIcon) {
        this.cfId = cfId;
        this.cfName = cfName;
        this.cfIcon = cfIcon;
    }

    public String getCfIcon() {
        return cfIcon;
    }

    public void setCfIcon(String cfIcon) {
        this.cfIcon = cfIcon;
    }

    public String getCfName() {
        return cfName;
    }

    public void setCfName(String cfName) {
        this.cfName = cfName;
    }

    public String getCfId() {
        return cfId;
    }

    public void setCfId(String cfId) {
        this.cfId = cfId;
    }

    @Override
    public String toString() {
        return "courseFeatures{" +
                "cfId='" + cfId + '\'' +
                ", cfName='" + cfName + '\'' +
                ", cfIcon='" + cfIcon + '\'' +
                '}';
    }
}
