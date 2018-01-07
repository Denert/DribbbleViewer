package com.example.varia.dribbbleviewer.model.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.varia.dribbbleviewer.R;
import com.example.varia.dribbbleviewer.ui.holder.BaseViewHolder;

public abstract class BaseViewModel {

    public abstract LayoutTypes getType();

    public BaseViewHolder createViewHolder(ViewGroup parent){
        return onCreateViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(getType().getValue(), parent, false));
    }

    protected abstract BaseViewHolder onCreateViewHolder(View view);

    public enum LayoutTypes{
        ShotItem(R.layout.item_shot),
        ProgressItem(R.layout.item_loading);



        private final int id;

        LayoutTypes(int id){
            this.id = id;
        }

        public int getValue(){
            return id;
        }
    }

    public boolean isItemDecorator() {
        return false;
    }

}
