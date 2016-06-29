package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 6/28/2016.
 */
public class Testimonials  implements Serializable {

    private String tName;
    private String tDesc;
    private String tImage;

    public Testimonials() {
    }

    public Testimonials(String tName, String tDesc, String tImage) {
        this.tName = tName;
        this.tDesc = tDesc;
        this.tImage = tImage;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettDesc() {
        return tDesc;
    }

    public void settDesc(String tDesc) {
        this.tDesc = tDesc;
    }

    public String gettImage() {
        return tImage;
    }

    public void settImage(String tImage) {
        this.tImage = tImage;
    }

    @Override
    public String toString() {
        return "Testimonials{" +
                "tName='" + tName + '\'' +
                ", tDesc='" + tDesc + '\'' +
                ", tImage='" + tImage + '\'' +
                '}';
    }
}
