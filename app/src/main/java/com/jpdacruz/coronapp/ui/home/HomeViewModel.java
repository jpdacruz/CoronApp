package com.jpdacruz.coronapp.ui.home;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jpdacruz.coronapp.MyApp;
import com.jpdacruz.coronapp.db.clases.HomeEntity;
import com.jpdacruz.coronapp.db.constantes.Constantes;
import com.jpdacruz.coronapp.db.remote.Repository;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    //dato a devolver
    private MutableLiveData<HomeEntity> mHomeEntity;
    //dato a trabajar
    private Repository repository;

    public HomeViewModel() {

        //se instancia el repo con sus metodos
        repository = new Repository();
        //se asigna a los datos a devolver el metodo del repositorio
        mHomeEntity = repository.getHomeEntity();
    }

    public LiveData<HomeEntity> getText() {
        //metodo get de devolucion
        return mHomeEntity;
    }
}