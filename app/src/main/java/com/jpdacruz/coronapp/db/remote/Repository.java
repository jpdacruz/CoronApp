package com.jpdacruz.coronapp.db.remote;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jpdacruz.coronapp.MyApp;
import com.jpdacruz.coronapp.db.clases.CountryEntity;
import com.jpdacruz.coronapp.db.clases.HomeEntity;
import com.jpdacruz.coronapp.db.constantes.Constantes;
import com.jpdacruz.coronapp.interfaces.InterfaceGeneral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Repository implements InterfaceGeneral {

    private static final String TAG = "Repository";

    //dato a devolver
    private MutableLiveData<HomeEntity> mHomeEntity;
    private MutableLiveData<List<CountryEntity>> mCountryEntity;
    //dato a trabajar
    private HomeEntity homeEntity;
    private ArrayList<CountryEntity> countryEntityList;

    public Repository() {

        //constructor vacio / se instancian los datos
        homeEntity = new HomeEntity();
        countryEntityList = new ArrayList<>();
        mHomeEntity = new MutableLiveData<>();
        mCountryEntity = new MutableLiveData<>();

        volleyGetAll();
        volleyGetCountries();
    }

    //getters
    public MutableLiveData<HomeEntity> getHomeEntity() {

        return mHomeEntity;
    }

    public MutableLiveData<List<CountryEntity>> getContryList(){

        return mCountryEntity;
    }

    //metodos volley
    private void volleyGetCountries() {

        String url = Constantes.URL_COUNTRIES;

        RequestQueue requestQueue = Volley.newRequestQueue(MyApp.getContext());

        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {

                            //Log.e(TAG, "Response: " + response);

                            try {

                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject data = jsonArray.getJSONObject(i);

                                    CountryEntity countryEntity = new CountryEntity();
                                    countryEntity.setCountry(data.getString("country"));
                                    countryEntity.setCases(data.getString("cases"));
                                    countryEntity.setTodayCases(data.getString("todayCases"));
                                    countryEntity.setDeaths(data.getString("deaths"));
                                    countryEntity.setTodayDeaths(data.getString("todayDeaths"));
                                    countryEntity.setRecovered(data.getString("recovered"));
                                    countryEntity.setActive(data.getString("active"));
                                    countryEntity.setCritical(data.getString("critical"));
                                    countryEntity.setCasesPerOneMillion(data.getString("casesPerOneMillion"));
                                    countryEntity.setDeathsPerOneMillion(data.getString("deathsPerOneMillion"));
                                    countryEntity.setUpdated(getHoraUpdate(data.getLong("updated")));

                                    countryEntityList.add(countryEntity);
                                }

                                mCountryEntity.setValue(countryEntityList);

                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.e(TAG,"RESPONSE: " +error);
                            }
                        });

        requestQueue.add(stringRequest);
    }

    private void volleyGetAll() {


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
                            homeEntity.setUpdated(getHoraUpdate(jsonObject.getLong("updated")));

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

    public String getAffectCountries(){

        return homeEntity.getAffectedCountries();
    }

    public String getHoraUpdate(long milliSecond) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, hh:mm:ss aaa");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);

        return formatter.format(calendar.getTime());
    }
}
