package com.example.varia.dribbbleviewer.ui.holder;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.varia.dribbbleviewer.model.view.BaseViewModel;

public abstract class BaseViewHolder<Item extends BaseViewModel> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindViewHolder(Item item);

    public abstract void unbindViewHolder();
}
