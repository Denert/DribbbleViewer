package com.example.varia.dribbbleviewer.common;


import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;

import com.example.varia.dribbbleviewer.model.Shot;
import com.example.varia.dribbbleviewer.model.view.BaseViewModel;
import com.example.varia.dribbbleviewer.model.view.ProgressItemViewModel;
import com.example.varia.dribbbleviewer.model.view.ShotsItemViewModel;
import com.example.varia.dribbbleviewer.ui.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder<BaseViewModel>> {

    List<BaseViewModel> list = new ArrayList<>();
    private ArrayMap<Integer, BaseViewModel> mTypeInstances = new ArrayMap<>();


    @Override
    public BaseViewHolder<BaseViewModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        return mTypeInstances.get(viewType).createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<BaseViewModel> holder, int position) {
        holder.bindViewHolder(getItem(position));
    }

    @Override
    public void onViewRecycled(BaseViewHolder<BaseViewModel> holder) {
        super.onViewRecycled(holder);
        holder.unbindViewHolder();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().getValue();
    }

    private BaseViewModel getItem(int position) {
        return list.get(position);
    }

    private void registerTypeInstance(BaseViewModel item){
            mTypeInstances.put(item.getType().getValue(), item);
    }

    public void addItem(List<? extends BaseViewModel> newItems){

        for (BaseViewModel newItem : newItems) {
            registerTypeInstance(newItem);
        }

        if (list.size() + newItems.size() < 51) {
            list.addAll(newItems);
        } else if (list.size() < 51){
            int size = list.size();
            for (int i = 0; i < 51 - size; i++)
                list.add(newItems.get(i));
            deleteItem();
        }

        Log.d("LIST_SIZE", String.valueOf(list.size()));

        notifyDataSetChanged();
    }

    public void setItems(List<BaseViewModel> items){
        list.clear();
        addItem(items);
    }

    public int getRealItemCount() {
        int count = 0;

        for (int i = 0; i < getItemCount(); i++){
            if (!getItem(i).isItemDecorator()){
                count++;
            }
        }

        return count;
    }

    public void insertItem(BaseViewModel item){
        registerTypeInstance(item);

        list.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void deleteItem(){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i) instanceof ProgressItemViewModel){
                list.remove(i);
                notifyItemRemoved(i);
                /*notifyItemRemoved(i - 5);*/
            }
        }
        /*if (list.get(list.size() - 1) instanceof ProgressItemViewModel) {
            list.remove(list.get(list.size() - 1));
            notifyItemRemoved(list.size() - 1);
        }*/
    }



    //TODO: add "addItems, setItems, etc"
}
