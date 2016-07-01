package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 6/28/2016.
 */
public class HomePopularCoursesModel implements Serializable {

    private int cId;
    private String cName;
    private String cIcon;
    private int cdPrice;
    private String catName;

    public HomePopularCoursesModel() {
    }

    public HomePopularCoursesModel(int cId, String cName, String cIcon, int cdPrice, String catName) {
        this.cId = cId;
        this.cName = cName;
        this.cIcon = cIcon;
        this.cdPrice = cdPrice;
        this.catName = catName;
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
        return "HomePopularCoursesModel{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", cIcon='" + cIcon + '\'' +
                ", cdPrice=" + cdPrice +
                ", catName='" + catName + '\'' +
                '}';
    }
}
