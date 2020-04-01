package com.jpdacruz.coronapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.clases.HomeEntity;
import com.jpdacruz.coronapp.db.constantes.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    //vars
    private static final String TAG = "HomeFragment";
    private HomeEntity homeEntity;
    //widgets
    private TextView totalConfirmados, totalFallecidos, totalRecuperados;
    private ProgressBar progressBar;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        iniciarComponentes(root);
        getData();

        return root;
    }

    private void iniciarComponentes(View root) {

        Log.d(TAG,"INICIANDO COMPONENTES");

        totalConfirmados = root.findViewById(R.id.textViewConfirmadosNumber);
        totalFallecidos = root.findViewById(R.id.textViewFallecidosNumber);
        totalRecuperados = root.findViewById(R.id.textViewRecuperadosNumber);
        progressBar = root.findViewById(R.id.progressBar);
    }

    private void getData() {

        //lamada al request de volley
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        //llamada get al sitio
        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, Constantes.URL_ALL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            //seteo textview de los totales

                            /*
                            homeEntity = new HomeEntity();
                            homeEntity.setCases(jsonObject.getString("cases"));
                            homeEntity.setDeaths(jsonObject.getString("deaths"));
                            homeEntity.setRecovered(jsonObject.getString("recovered"));
                            */

                            totalConfirmados.setText(jsonObject.getString("cases"));
                            totalFallecidos.setText(jsonObject.getString("deaths"));
                            totalRecuperados.setText(jsonObject.getString("recovered"));

                        } catch (JSONException e) {
                            //capturo el error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //se esconde el progressbar
                        progressBar.setVisibility(View.GONE);
                        //RECORDAR!!! error.tostring porque si no no se ve el codigo de error
                        Log.d(TAG, "ERROR RESPONSE " + error.toString());
                    }
                });
        //agrego al queue el request

        queue.add(stringRequest);

    }


}
