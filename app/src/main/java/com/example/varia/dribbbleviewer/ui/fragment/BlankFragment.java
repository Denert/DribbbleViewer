package com.example.varia.dribbbleviewer.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.varia.dribbbleviewer.MyApplication;
import com.example.varia.dribbbleviewer.R;
import com.example.varia.dribbbleviewer.common.BaseAdapter;
import com.example.varia.dribbbleviewer.common.MyLinearLayoutManager;
import com.example.varia.dribbbleviewer.common.NetworkManager;
import com.example.varia.dribbbleviewer.model.view.BaseViewModel;
import com.example.varia.dribbbleviewer.model.view.ProgressItemViewModel;
import com.example.varia.dribbbleviewer.mvp.presenter.BasePresenter;
import com.example.varia.dribbbleviewer.mvp.view.BaseView;
import com.example.varia.dribbbleviewer.rest.api.ShotApi;
import com.example.varia.dribbbleviewer.ui.activity.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends MvpAppCompatFragment implements BaseView {

    @Inject
    ShotApi mShotApi;

    @Inject
    NetworkManager mNetworkManager;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swRefresh;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @InjectPresenter
    BasePresenter mBasePresenter;
    BaseAdapter mBaseAdapter;
    MyLinearLayoutManager linearLayoutManager;

    public BlankFragment() {
        // Required empty public constructor
        MyApplication.getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setUpSwipeToRefresh();
        setUpRecyclerView();
        mBasePresenter.loadStart();
    }

    @Override
    public void showRefreshing() {

    }

    @Override
    public void hideRefreshing() {
        swRefresh.setRefreshing(false);
    }

    @Override
    public void showListProgress() {
        rvList.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListProgress() {
        progressBar.setVisibility(View.GONE);
        rvList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPaginationProgress() {
        int total = linearLayoutManager.getTotalItemsCount();
        if (linearLayoutManager.getTotalItemsCount() < 50){
            mBaseAdapter.insertItem(new ProgressItemViewModel());
        } else {
            mBaseAdapter.deleteItem();
        }
    }

    @Override
    public void hidePaginationProgress() {
        mBaseAdapter.deleteItem();
    }

    @Override
    public void showError(String message) {
        Toast.makeText((MainActivity) getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setItems(List<BaseViewModel> list) {
        mBaseAdapter.setItems(list);
    }

    @Override
    public void addItems(List<BaseViewModel> list) {
        mBaseAdapter.addItem(list);
    }

    private void setUpRecyclerView(){
        linearLayoutManager = new MyLinearLayoutManager(getActivity());
        mBaseAdapter = new BaseAdapter();
        rvList.setAdapter(mBaseAdapter);
        rvList.setLayoutManager(linearLayoutManager);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (linearLayoutManager.isOnNextPagePosition())
                    mBasePresenter.loadNext();
                if (linearLayoutManager.isLastItem()){
                    int m = linearLayoutManager.getTotalItemsCount();
                    hidePaginationProgress();
                }
            }
        });
    }

    private void setUpSwipeToRefresh(){
        swRefresh.setOnRefreshListener(() -> {
            mBasePresenter.loadRefresh();
        });
        swRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_red_light);
    }
}
