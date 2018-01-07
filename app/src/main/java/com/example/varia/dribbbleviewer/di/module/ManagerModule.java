package com.example.varia.dribbbleviewer.di.module;





import com.example.varia.dribbbleviewer.common.NetworkManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ManagerModule {

    @Provides
    @Singleton
    NetworkManager provideNetworkManager() {
        return new NetworkManager();
    }

}
