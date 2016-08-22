package com.simplelecture.main.model.viewmodel;

/**
 * Created by M1032185 on 8/18/2016.
 */
public class ForumTopics {

    private String Id;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    private String Name;

    public void ForumTopics(String Id, String Name){

        this.Id = Id;
        this.Name = Name;

    }
}
