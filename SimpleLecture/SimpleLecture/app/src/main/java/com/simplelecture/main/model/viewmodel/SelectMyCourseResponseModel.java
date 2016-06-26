package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 6/26/2016.
 */
public class SelectMyCourseResponseModel implements Serializable {

    private String Id;
    private String Name;
    private boolean selected;

    public SelectMyCourseResponseModel() {
    }

    public SelectMyCourseResponseModel(String id, String name, boolean selected) {
        Id = id;
        Name = name;
        this.selected = selected;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "SelectMyCourseResponseModel{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", selected=" + selected +
                '}';
    }
}
