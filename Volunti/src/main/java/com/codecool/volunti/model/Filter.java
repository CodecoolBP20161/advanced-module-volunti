package com.codecool.volunti.model;


import lombok.Data;

import java.io.Serializable;


public class Filter implements Serializable {

    private String oppId;
    private String title;


    public Filter() {
    }

    public Filter(String oppId, String title) {
        this.oppId = oppId;
        this.title = title;
    }

    public String getOppId() {
        return oppId;
    }

    public void setOppId(String oppId) {
        this.oppId = oppId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "oppId='" + oppId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
