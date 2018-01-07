package com.example.varia.dribbbleviewer.rest.api;


import com.example.varia.dribbbleviewer.model.Shot;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ShotApi {

    @GET("shots")
    Observable<ArrayList<Shot>> getShots(@QueryMap Map<String, String> map);
}
