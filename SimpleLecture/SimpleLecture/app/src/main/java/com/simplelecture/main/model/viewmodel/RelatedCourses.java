package com.simplelecture.main.model.viewmodel;

/**
 * Created by M1032185 on 8/18/2016.
 */
public class RelatedCourses {

    private String cId;
    private String cName;
    private String cIcon;

    public RelatedCourses() {
    }

    public void RelatedCourses(String cId, String cName, String cIcon) {
        this.cId = cId;
        this.cName = cName;
        this.cIcon = cIcon;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcIcon() {
        return cIcon;
    }

    public void setcIcon(String cIcon) {
        this.cIcon = cIcon;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    @Override
    public String toString() {
        return "RelatedCourses{" +
                "cId='" + cId + '\'' +
                ", cName='" + cName + '\'' +
                ", cIcon='" + cIcon + '\'' +
                '}';
    }
}
