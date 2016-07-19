package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 7/19/2016.
 */
public class CourseMaterials implements Serializable {


    private String Id;
    private String Name;
    private String Price;

    public CourseMaterials() {
    }

    public CourseMaterials(String id, String name, String price) {
        Id = id;
        Name = name;
        Price = price;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "CourseMaterials{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Price='" + Price + '\'' +
                '}';
    }
}
