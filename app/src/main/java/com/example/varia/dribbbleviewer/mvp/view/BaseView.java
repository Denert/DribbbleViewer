package com.example.varia.dribbbleviewer.mvp.view;


import com.arellomobile.mvp.MvpView;
import com.example.varia.dribbbleviewer.model.Shot;
import com.example.varia.dribbbleviewer.model.view.BaseViewModel;

import java.util.List;

public interface BaseView extends MvpView {

    void showRefreshing();
    void hideRefreshing();

    void showListProgress();
    void hideListProgress();

    void showPaginationProgress();
    void hidePaginationProgress();

    void showError(String message);

    void setItems(List<BaseViewModel> list);
    void addItems(List<BaseViewModel> list);

}
