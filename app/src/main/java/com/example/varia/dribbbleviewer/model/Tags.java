package com.example.varia.dribbbleviewer.model;

import io.realm.RealmObject;


public class Tags extends RealmObject{
    public String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
