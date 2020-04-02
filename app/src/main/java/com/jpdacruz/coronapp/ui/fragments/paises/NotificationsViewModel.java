package com.jpdacruz.coronapp.ui.fragments.paises;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jpdacruz.coronapp.db.clases.CountryEntity;
import com.jpdacruz.coronapp.db.remote.Repository;

import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private static final String TAG = "NotificationsViewModel";

    private MutableLiveData<List<CountryEntity>> mCountriesList;
    private Repository repository;

    public NotificationsViewModel() {
        repository = new Repository();
        mCountriesList = repository.getContryList();

    }

    public MutableLiveData<List<CountryEntity>> getmCountriesList() {
        return mCountriesList;
    }
}