package com.aps.user.sametime;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class User extends Application {
    String name;
    String id;


    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

    }
}