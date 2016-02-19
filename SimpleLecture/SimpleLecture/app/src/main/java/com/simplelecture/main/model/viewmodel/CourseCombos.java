package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 2/18/2016.
 */
public class CourseCombos implements Serializable {

    private int cId;
    private String cName;
    private String cIcon;
    private int cdPrice;
    private String catName;

    public CourseCombos() {
    }

    public CourseCombos(int cId, String catName, int cdPrice, String cIcon, String cName) {
        this.cId = cId;
        this.catName = catName;
        this.cdPrice = cdPrice;
        this.cIcon = cIcon;
        this.cName = cName;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcIcon() {
        return cIcon;
    }

    public void setcIcon(String cIcon) {
        this.cIcon = cIcon;
    }

    public int getCdPrice() {
        return cdPrice;
    }

    public void setCdPrice(int cdPrice) {
        this.cdPrice = cdPrice;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public String toString() {
        return "courseCombos{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", cIcon='" + cIcon + '\'' +
                ", cdPrice=" + cdPrice +
                ", catName='" + catName + '\'' +
                '}';
    }
}
