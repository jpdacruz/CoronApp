package com.jpdacruz.coronapp.ui.paises;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

    private ArrayList<CountryEntity> countryEntityList;
    private NotificationsViewModel notificationsViewModel;

    //widgets
    private RecyclerView recyclerViewCountries;
    private ProgressBar progressBar;
    private CountriesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_paises, container, false);

        iniciarComponentes(root);
        iniciarDatosVolley();

        return root;
    }

    private void iniciarComponentes(View root) {

        recyclerViewCountries = root.findViewById(R.id.recyclerPaises);
        recyclerViewCountries.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = root.findViewById(R.id.progressBarPaises);
    }

    private void iniciarDatosVolley() {

        String url = Constantes.URL_COUNTRIES;
        countryEntityList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);

                if (response != null) {

                    Log.e(TAG, "Response: " + response);

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject data = jsonArray.getJSONObject(i);

                            CountryEntity countryEntity = new CountryEntity();
                            countryEntity.setCountry(data.getString("country"));
                            countryEntity.setCases(data.getString("cases"));
                            countryEntity.setDeaths(data.getString("deaths"));

                            countryEntityList.add(countryEntity);
                        }
                        iniciarAdapter();

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         progressBar.setVisibility(View.GONE);
                         Log.e(TAG,"RESPONSE: " +error);
                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void iniciarAdapter() {

        adapter = new CountriesAdapter(countryEntityList, getActivity());
        recyclerViewCountries.setAdapter(adapter);



    }
}
