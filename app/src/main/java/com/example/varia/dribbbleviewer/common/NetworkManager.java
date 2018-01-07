package com.example.varia.dribbbleviewer.common;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.varia.dribbbleviewer.MyApplication;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.internal.Util;

public class NetworkManager {

    @Inject
    Context mContext;

    public NetworkManager() {
        MyApplication.getApplicationComponent().inject(this);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean bool = Util.isEmulator();
        return ((networkInfo != null && networkInfo.isConnected()) || Util.isEmulator());
    }

    private Callable<Boolean> isApiReachableCallable() {
        return () -> {
            try {
                if (!isOnline())
                    return false;

                URL url = new URL("https://api.vk.com");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(2000);
                connection.connect();

                return true;
            } catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
                Log.d("TAG", e.getMessage());
                return false;
            }
        };
    }

    public Observable<Boolean> getNetworkState(){
        return Observable.fromCallable(isApiReachableCallable());
    }
}
