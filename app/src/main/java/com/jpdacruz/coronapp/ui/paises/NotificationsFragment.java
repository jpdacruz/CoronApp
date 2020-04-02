package com.jpdacruz.coronapp.ui.paises;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jpdacruz.coronapp.MyApp;
import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.clases.CountryEntity;
import com.jpdacruz.coronapp.db.constantes.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    //vars
    private static final String TAG = "NotificationsFragment";

    private List<CountryEntity> countryEntityList;
    private NotificationsViewModel notificationsViewModel;

    //widgets
    private TextView mCountry, mCases, mDeath;
    private RecyclerView recyclerViewCountries;
    private ProgressBar progressBar;
    private CountriesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_paises, container, false);

        iniciarComponentes(root);
        iniciarAdapter();
        iniciarViewModel();

        return root;
    }

    private void iniciarComponentes(View root) {

        recyclerViewCountries = root.findViewById(R.id.recyclerPaises);
        progressBar = root.findViewById(R.id.progressBarPaises);
        mCountry = root.findViewById(R.id.textViewPais);
        mCases = root.findViewById(R.id.textViewCountriesConfirmadosNumber);
        mDeath = root.findViewById(R.id.textViewCountriesFallecidosNumber);
    }

    private void iniciarAdapter() {

        recyclerViewCountries.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CountriesAdapter(countryEntityList, getActivity());
        recyclerViewCountries.setAdapter(adapter);
    }

    private void iniciarViewModel() {

        notificationsViewModel.getmCountriesList().observe(getViewLifecycleOwner(), new Observer<List<CountryEntity>>() {
            @Override
            public void onChanged(List<CountryEntity> countryEntities) {

                countryEntityList = countryEntities;
                adapter.setData(countryEntityList);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
