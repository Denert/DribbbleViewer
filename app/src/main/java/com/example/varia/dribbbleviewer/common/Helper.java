package com.example.varia.dribbbleviewer.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

import com.example.varia.dribbbleviewer.model.Shot;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static int getItemHeight(Context context, Display display) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        int actionBarHeight = 0;
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize }
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);

        Point size = new Point();
        display.getSize(size);
        if (context.getResources().getConfiguration().orientation == 1)
            return (size.y - statusBarHeight - actionBarHeight) / 2;
        else return  (size.x - statusBarHeight - actionBarHeight) / 2;
    }

    public static List<Shot> shotFilter(List<Shot> shots){
        List<Shot> items = new ArrayList<>();
        for (Shot item : shots){
            if (!item.getAnimated())
                items.add(item);
        }
        return items;
    }

    public static List<Shot> restoreDataFilter(List<Shot> shots){
        ArrayList<Shot> temp = new ArrayList<>();
        for (int i = 0; i < shots.size(); i++){
            if (i < 50) {
                temp.add(shots.get(i));
            }
        }
        return temp;
    }

}
