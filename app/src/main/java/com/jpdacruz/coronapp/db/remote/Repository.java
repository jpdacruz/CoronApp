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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Repository implements InterfaceGeneral {

    private static final String TAG = "Repository";

    //dato a devolver
    private MutableLiveData<HomeEntity> mHomeEntity;
    private MutableLiveData<List<CountryEntity>> mCountryEntity;
    //dato a trabajar
    private HomeEntity homeEntity;
    private ArrayList<CountryEntity> countryEntityList;

    /**
     * constructor del repositorio -> vacio
     */
    public Repository() {

        /**
         * instancia de datos a trabajar
         */
        homeEntity = new HomeEntity();
        countryEntityList = new ArrayList<>();

        /**
         * instancia de datos a devolver
         */
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

        /**
         * obtiene el contexto de la clase MyApp
         */
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
                                    JSONObject countryInfo = data.getJSONObject("countryInfo");

                                    CountryEntity countryEntity = new CountryEntity();

                                    /**
                                     * cambia nombre Fakland por Malvinas
                                     */
                                    if (data.getString("country").equals("Falkland Islands (Malvinas)")){

                                        countryEntity.setCountry("Islas Malvinas");
                                        countryEntity.setFlag("https://raw.githubusercontent.com/NovelCOVID/API/master/assets/flags/ar.png");

                                    }else {

                                        countryEntity.setCountry(data.getString("country"));
                                        countryEntity.setFlag(countryInfo.getString("flag"));
                                    }

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

                                /**
                                 * ordena los paises por cantidad de fallecidos
                                 */
                                mCountryEntity.setValue(ordenarPaises(countryEntityList));
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

    /**
     * metodo para obtener los datos a nivel global
     */
    private void volleyGetAll() {

        //llamada al request de volley
        RequestQueue queue = Volley.newRequestQueue(MyApp.getContext());

        //llamada get al sitio
        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, Constantes.URL_ALL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

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

                        //RECORDAR!!! error.tostring porque si no, no se ve el codigo de error
                        Log.d(TAG, "ERROR RESPONSE " + error.toString());
                    }
                });
        //agrego al queue el request
        queue.add(stringRequest);
    }

    private ArrayList<CountryEntity> ordenarPaises(ArrayList<CountryEntity> countryEntityList) {

        /**
         * ordenamiento burbuja de paises
         * por cantidad de fallecidos
         *
         * VER PORQUE NO SE PUDO ORDENAR POR COMPARABLE O POR SORT
         */
        int in;

        for (int i = 1 ; i < countryEntityList.size() ; i++) {
            CountryEntity aux = countryEntityList.get(i);
            in = i;

            while (in > 0 && countryEntityList.get(in - 1).getDeaths() < aux.getDeaths()) {
                countryEntityList.set(in, countryEntityList.get(in - 1));    //desplaza el elemento hacia la derecha
                --in;
            }

            countryEntityList.set(in, aux);    //inserta elemento
        }

        return countryEntityList;
    }

    /**
     * retorna el valor de la cantidad de paise afectados
     * @return
     */
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
