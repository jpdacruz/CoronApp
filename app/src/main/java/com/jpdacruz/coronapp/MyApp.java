package com.jpdacruz.coronapp;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    private static MyApp instace;

    public static MyApp getInstance(){

        return instace;
    }

    public static Context getContext(){

        return instace;
    }

    @Override
    public void onCreate() {
        instace = this;
        super.onCreate();
    }
}
