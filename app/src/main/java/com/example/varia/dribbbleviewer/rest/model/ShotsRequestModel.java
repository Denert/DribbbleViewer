package com.example.varia.dribbbleviewer.rest.model;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class ShotsRequestModel {

    @SerializedName("access_token")
    String token = "1b2a78c946de4647ba95a7bf5e134f9dbe8c76578b44e80f762d3e80a671c117";

    @SerializedName("sort")
    String sort = "recent";

    @SerializedName("page")
    int page = 1;

    public String getToken() {
        return token;
    }

    public String getSort() {
        return sort;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ShotsRequestModel(int page) {
        this.page = page;
    }

    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();

        map.put("access_token", getToken());
        map.put("sort", getSort());
        map.put("page", String.valueOf(getPage()));

        return map;
    }

}
