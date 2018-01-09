package com.example.varia.dribbbleviewer.mvp.presenter;


import android.support.test.espresso.contrib.CountingIdlingResource;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.varia.dribbbleviewer.MyApplication;
import com.example.varia.dribbbleviewer.common.Helper;
import com.example.varia.dribbbleviewer.common.NetworkManager;
import com.example.varia.dribbbleviewer.model.Shot;
import com.example.varia.dribbbleviewer.model.view.BaseViewModel;
import com.example.varia.dribbbleviewer.model.view.ShotsItemViewModel;
import com.example.varia.dribbbleviewer.mvp.view.BaseView;
import com.example.varia.dribbbleviewer.rest.api.ShotApi;
import com.example.varia.dribbbleviewer.rest.model.ShotsRequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;


@InjectViewState
public class BasePresenter extends MvpPresenter<BaseView> {

    private boolean isLoading;
    private boolean isDbData = false;
    private int page = 1;
    private int shotId = 1;

    @Inject
    NetworkManager mNetworkManager;

    @Inject
    ShotApi mShotApi;

    public BasePresenter() {
        MyApplication.getApplicationComponent().inject(this);
    }

    private void loadData(ProgressType progressType, boolean isNextPage){

        if (isLoading)
            return;

        if (isNextPage)
            page++;

        isLoading = true;

        mNetworkManager.getNetworkState()
                .flatMap(aBoolean -> {
                    if (!aBoolean && page > 1) {
                        return Observable.empty();
                    }
                    return aBoolean
                            ? onCreateLoadDataObservable(page, isNextPage)
                            : onCreateRestoreDataObservable();

                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    onLoadStart(progressType);
                })
                .doFinally(() -> {
                    getViewState().hidePaginationProgress();
                    onLoadingFinish(progressType);
                })
                .subscribe(repositories -> {
                    onLoadingSuccess(progressType, repositories);
                }, error -> {
                    error.printStackTrace();
                    onLoadingFailed(error);
                });
    }

    public enum ProgressType{
        Refreshing, ListProgress, Paging
    }

    public void showProgress(ProgressType progressType){
        switch (progressType){
            case Refreshing:
                getViewState().showRefreshing();
                break;
            case ListProgress:
                getViewState().showListProgress();
                break;
        }
    }

    public void hideProgress(ProgressType progressType){
        switch (progressType){
            case Refreshing:
                getViewState().hideRefreshing();
                break;
            case ListProgress:
                getViewState().hideListProgress();
                break;
        }
    }

    public void loadStart(){
        loadData(ProgressType.ListProgress, false);
    }

    public void loadNext(){
        loadData(ProgressType.Paging, true);
    }

    public void loadRefresh(){
        page = 1;
        shotId = 1;
        loadData(ProgressType.Refreshing, false);
    }

    public void onLoadStart(ProgressType progressType){
        showProgress(progressType);
    }

    public void onLoadingFinish(ProgressType progressType){
        isLoading = false;
        hideProgress(progressType);

        if (!isDbData)
            getViewState().showPaginationProgress();
    }

    public void onLoadingFailed(Throwable throwable){
        getViewState().showError(throwable.getMessage());
    }

    public void onLoadingSuccess(ProgressType progressType, List<BaseViewModel> items){
        if(progressType == ProgressType.Paging){
            getViewState().addItems(items);
        } else {
            getViewState().setItems(items);
        }
    }

    public void saveToDb(RealmObject item){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }

    public Observable<BaseViewModel> onCreateLoadDataObservable(int page, boolean isNextPage) {
        if (!isNextPage) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }

        Log.d("PAGE", String.valueOf(page));

        isDbData = false;

        return mShotApi.getShots(new ShotsRequestModel(page).toMap())
                .flatMap(shots -> Observable.fromIterable(Helper.shotFilter(shots)))
                .doOnNext(shot -> {
                    shot.setId(shotId++);
                })
                .doOnNext(this::saveToDb)
                .flatMap(shots -> {
                    List<BaseViewModel> baseItems = new ArrayList<>();
                    baseItems.add(new ShotsItemViewModel(shots));
                    return Observable.fromIterable(baseItems);
                });
    }

    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        isDbData = true;

        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(shots -> Observable.fromIterable(Helper.restoreDataFilter(shots)))
                .flatMap(shot -> {
                    List<BaseViewModel> baseItems = new ArrayList<>();
                    baseItems.add(new ShotsItemViewModel(shot));
                    return Observable.fromIterable(baseItems);
                });
    }

    public Callable<List<Shot>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {"id"};
            Sort[] sortOrder = {Sort.ASCENDING};

            Realm realm = Realm.getDefaultInstance();
            RealmResults<Shot> realmResults = realm.where(Shot.class)
                    .findAllSorted(sortFields, sortOrder);
            return realm.copyFromRealm(realmResults);
        };
    }

}
