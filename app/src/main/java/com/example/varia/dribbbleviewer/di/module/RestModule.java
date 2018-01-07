package com.example.varia.dribbbleviewer.di.module;

import com.example.varia.dribbbleviewer.rest.RestClient;
import com.example.varia.dribbbleviewer.rest.api.ShotApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RestModule {

    private RestClient mRestClient;

    public RestModule() {
        mRestClient = new RestClient();
    }

    @Singleton
    @Provides
    public RestClient provideRestClient() {
        return mRestClient;
    }

    @Singleton
    @Provides
    public ShotApi provideShotsApi(){
        return mRestClient.createService(ShotApi.class);
    }

}
