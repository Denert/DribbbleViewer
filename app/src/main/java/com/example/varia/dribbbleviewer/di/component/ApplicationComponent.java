package com.example.varia.dribbbleviewer.di.component;

import com.example.varia.dribbbleviewer.ui.fragment.BlankFragment;
import com.example.varia.dribbbleviewer.ui.activity.MainActivity;
import com.example.varia.dribbbleviewer.common.NetworkManager;
import com.example.varia.dribbbleviewer.di.module.ApplicationModule;
import com.example.varia.dribbbleviewer.di.module.ManagerModule;
import com.example.varia.dribbbleviewer.di.module.RestModule;
import com.example.varia.dribbbleviewer.model.view.ShotsItemViewModel;
import com.example.varia.dribbbleviewer.mvp.presenter.BasePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ManagerModule.class, RestModule.class})
public interface ApplicationComponent {

    void inject(BlankFragment fragment);

    void inject(MainActivity activity);

    void inject(NetworkManager manager);

    void inject(ShotsItemViewModel.ShotViewHolder holder);

    void inject(BasePresenter presenter);

}
