package com.jpdacruz.coronapp.db.remote;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jpdacruz.coronapp.MyApp;
import com.jpdacruz.coronapp.db.clases.HomeEntity;
import com.jpdacruz.coronapp.db.constantes.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

public class Repository {

    private static final String TAG = "Repository";

    //dato a devolver
    private MutableLiveData<HomeEntity> mHomeEntity;
    //dato a trabajar
    private HomeEntity homeEntity;

    public Repository() {

        //constructor vacio / se instancian los datos
        homeEntity = new HomeEntity();
        mHomeEntity = new MutableLiveData<>();

        //lamada al request de volley
        RequestQueue queue = Volley.newRequestQueue(MyApp.getContext());

        //llamada get al sitio
        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, Constantes.URL_ALL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());

                            homeEntity = new HomeEntity();
                            homeEntity.setCases(jsonObject.getString("cases"));
                            homeEntity.setDeaths(jsonObject.getString("deaths"));
                            homeEntity.setRecovered(jsonObject.getString("recovered"));
                            homeEntity.setAffectedCountries(jsonObject.getString("affectedCountries"));

                            mHomeEntity.setValue(homeEntity);

                        } catch (JSONException e) {
                            //capturo el error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //RECORDAR!!! error.tostring porque si no no se ve el codigo de error
                        Log.d(TAG, "ERROR RESPONSE " + error.toString());
                    }
                });
        //agrego al queue el request
        queue.add(stringRequest);
    }

    public MutableLiveData<HomeEntity> getHomeEntity() {
        return mHomeEntity;
    }
}
