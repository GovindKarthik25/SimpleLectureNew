package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 7/6/2016.
 */
public class CourseCategoriesModel  implements Serializable {

    private int cId;
    private String cName;
    private String cIcon;
    private String cdPrice;
    private String catName;

    public CourseCategoriesModel() {
    }

    public CourseCategoriesModel(int cId, String cName, String cIcon, String cdPrice, String catName) {
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

    public String getCdPrice() {
        return cdPrice;
    }

    public void setCdPrice(String cdPrice) {
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
        return "CourseCategoriesModel{" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", cIcon='" + cIcon + '\'' +
                ", cdPrice='" + cdPrice + '\'' +
                ", catName='" + catName + '\'' +
                '}';
    }
}
