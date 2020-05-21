package com.jpdacruz.coronapp;

import android.app.Application;
import android.content.Context;

/**
 * Clase my app para obtener el contexto en volley
 * define context a nivel de aplicacion
 */
public class MyApp extends Application {

    private static MyApp instace;

    /**
     * devolver objeto MyApp como instance
     */
    public static MyApp getInstance(){

        return instace;
    }

    /**
     * devolver instacia como Context
     * @return
     */
    public static Context getContext(){

        return instace;
    }

    /**
     * en el onCreate asigna la instancia del activity creado a instance
     */
    @Override
    public void onCreate() {
        instace = this;
        super.onCreate();
    }
}
