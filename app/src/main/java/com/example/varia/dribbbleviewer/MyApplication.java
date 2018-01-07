package com.example.varia.dribbbleviewer;


import android.app.Application;

import com.example.varia.dribbbleviewer.di.component.ApplicationComponent;
import com.example.varia.dribbbleviewer.di.component.DaggerApplicationComponent;
import com.example.varia.dribbbleviewer.di.module.ApplicationModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.internal.RealmCore;

public class MyApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initComponent();
    }

    private void initComponent(){
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

}
