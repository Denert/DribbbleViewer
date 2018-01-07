package com.example.varia.dribbbleviewer.model.view;


import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varia.dribbbleviewer.MyApplication;
import com.example.varia.dribbbleviewer.R;
import com.example.varia.dribbbleviewer.common.Helper;
import com.example.varia.dribbbleviewer.model.Shot;
import com.example.varia.dribbbleviewer.ui.holder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotsItemViewModel extends BaseViewModel {

    private int mId;
    private String mTitle;
    private String mDescription;
    private String mHidpi;
    private String mNormal;
    private String mTeaser;
    private int mHeight;

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getHidpi() {
        return mHidpi;
    }

    public String getNormal() {
        return mNormal;
    }

    public String getTeaser() {
        return mTeaser;
    }

    public int getHeight() {
        return mHeight;
    }

    public ShotsItemViewModel(Shot shotItem) {
        this.mId = shotItem.getId();
        this.mTitle = shotItem.getTitle();
        this.mDescription = shotItem.getDescription();
        this.mHidpi = shotItem.getImages().getHidpi();
        this.mNormal = shotItem.getImages().getNormal();
        this.mTeaser = shotItem.getImages().getTeaser();
        this.mHeight = shotItem.getHeight();
    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.ShotItem;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new ShotViewHolder(view);
    }

    public static class ShotViewHolder extends BaseViewHolder<ShotsItemViewModel> {

        @BindView(R.id.tv_title)
        TextView title;

        @BindView(R.id.tv_description)
        TextView description;

        @BindView(R.id.iv_shot)
        ImageView shot;

        @Inject
        Context mContext;

        public ShotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            MyApplication.getApplicationComponent().inject(this);
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            shot.setMaxHeight(Helper.getItemHeight(mContext, windowManager.getDefaultDisplay()));
        }

        @Override
        public void bindViewHolder(ShotsItemViewModel shotsItemViewModel) {
            title.setText(shotsItemViewModel.getTitle());
            if (shotsItemViewModel.getDescription() != null) {
                description.setText(Html.fromHtml(shotsItemViewModel.getDescription()));
                description.setVisibility(View.VISIBLE);
            } else {
                description.setVisibility(View.GONE);
            }

            shot.getLayoutParams().height = shot.getMaxHeight();

            if (shotsItemViewModel.getHidpi() != null){
                Picasso.with(itemView.getContext())
                        .load(shotsItemViewModel.getHidpi())
                        .into(shot);
            } else if (shotsItemViewModel.getNormal() != null){
                Picasso.with(itemView.getContext())
                        .load(shotsItemViewModel.getNormal())
                        .into(shot);
            } else {
                Picasso.with(itemView.getContext())
                        .load(shotsItemViewModel.getTeaser())
                        .into(shot);
            }
        }

        @Override
        public void unbindViewHolder() {
            shot.setImageBitmap(null);
            title.setText(null);
            description.setText(null);
        }
    }
}
