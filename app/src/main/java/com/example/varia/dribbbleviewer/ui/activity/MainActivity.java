package com.example.varia.dribbbleviewer.ui.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.varia.dribbbleviewer.ui.fragment.BlankFragment;
import com.example.varia.dribbbleviewer.MyApplication;
import com.example.varia.dribbbleviewer.R;
import com.example.varia.dribbbleviewer.rest.api.ShotApi;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.internal.RealmCore;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Inject
    ShotApi mShotApi;

    @Inject
    Context mContext;

    private String DRIBBBLE_BASE_URL = "https://api.dribbble.com/v1/";

    private Retrofit mRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication.getApplicationComponent().inject(this);

        Realm.init(mContext);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = new BlankFragment();
            getSupportFragmentManager().
                    beginTransaction().add(R.id.main_content, fragment)
                    .commit();
        }
    }
}
