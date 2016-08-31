package com.simplelecture.main.model.viewmodel;

/**
 * Created by M1032185 on 8/18/2016.
 */
public class ForumTopics {

    private String Id;
    private String Name;

    public ForumTopics() {
    }

    public ForumTopics(String id, String name) {
        Id = id;
        Name = name;
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

    @Override
    public String toString() {
        return "ForumTopics{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
