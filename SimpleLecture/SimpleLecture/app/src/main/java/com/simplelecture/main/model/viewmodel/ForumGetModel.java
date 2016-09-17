package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 9/5/2016.
 */
public class ForumGetModel implements Serializable {

    private String Id;
    private String Name;
    private String Details;
    private String PostedBy;
    private String PostedDate;
    private String ReplyCount;
    private String PageUrlForumDetails;

    public ForumGetModel() {
    }

    public ForumGetModel(String id, String name, String details, String postedBy, String postedDate, String replyCount, String pageUrlForumDetails) {
        Id = id;
        Name = name;
        Details = details;
        PostedBy = postedBy;
        PostedDate = postedDate;
        ReplyCount = replyCount;
        PageUrlForumDetails = pageUrlForumDetails;
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

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getPostedBy() {
        return PostedBy;
    }

    public void setPostedBy(String postedBy) {
        PostedBy = postedBy;
    }

    public String getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(String postedDate) {
        PostedDate = postedDate;
    }

    public String getReplyCount() {
        return ReplyCount;
    }

    public void setReplyCount(String replyCount) {
        ReplyCount = replyCount;
    }

    public String getPageUrlForumDetails() {
        return PageUrlForumDetails;
    }

    public void setPageUrlForumDetails(String pageUrlForumDetails) {
        PageUrlForumDetails = pageUrlForumDetails;
    }

    @Override
    public String toString() {
        return "ForumGetModel{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Details='" + Details + '\'' +
                ", PostedBy='" + PostedBy + '\'' +
                ", PostedDate='" + PostedDate + '\'' +
                ", ReplyCount='" + ReplyCount + '\'' +
                ", PageUrlForumDetails='" + PageUrlForumDetails + '\'' +
                '}';
    }
}
