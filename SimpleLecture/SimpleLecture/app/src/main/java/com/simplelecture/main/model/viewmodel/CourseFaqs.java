package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 7/10/2016.
 */
public class CourseFaqs implements Serializable {

    private String cfId;
    private String cfName;
    private String cfAnswer;

    public CourseFaqs() {
    }

    public CourseFaqs(String cfId, String cfName, String cfAnswer) {
        this.cfId = cfId;
        this.cfName = cfName;
        this.cfAnswer = cfAnswer;
    }

    public String getCfId() {
        return cfId;
    }

    public void setCfId(String cfId) {
        this.cfId = cfId;
    }

    public String getCfName() {
        return cfName;
    }

    public void setCfName(String cfName) {
        this.cfName = cfName;
    }

    public String getCfAnswer() {
        return cfAnswer;
    }

    public void setCfAnswer(String cfAnswer) {
        this.cfAnswer = cfAnswer;
    }

    @Override
    public String toString() {
        return "CourseFaqs{" +
                "cfId='" + cfId + '\'' +
                ", cfName='" + cfName + '\'' +
                ", cfAnswer='" + cfAnswer + '\'' +
                '}';
    }
}
