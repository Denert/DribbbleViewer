package com.example.varia.dribbbleviewer.model.view;


import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.example.varia.dribbbleviewer.R;
import com.example.varia.dribbbleviewer.ui.holder.BaseViewHolder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressItemViewModel extends BaseViewModel {
    @Override
    public LayoutTypes getType() {
        return LayoutTypes.ProgressItem;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new ProgressViewHolder(view);
    }

    public static class ProgressViewHolder extends BaseViewHolder<ProgressItemViewModel>{

        @BindView(R.id.progressBar2)
        ProgressBar progressBar;



        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindViewHolder(ProgressItemViewModel progressItemViewModel) {

        }

        @Override
        public void unbindViewHolder() {

        }
    }
}
