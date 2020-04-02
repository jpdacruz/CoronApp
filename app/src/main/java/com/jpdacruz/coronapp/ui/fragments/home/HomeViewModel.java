package com.jpdacruz.coronapp.ui.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jpdacruz.coronapp.db.clases.HomeEntity;
import com.jpdacruz.coronapp.db.remote.Repository;

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