package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 12/17/2016.
 */

public class SplashResponseModel implements Serializable {

    private String Name;
    private String Link;
    private String Image;

    public SplashResponseModel() {
    }

    public SplashResponseModel(String name, String link, String image) {
        Name = name;
        Link = link;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "SplashResponseModel{" +
                "Name='" + Name + '\'' +
                ", Link='" + Link + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }
}
