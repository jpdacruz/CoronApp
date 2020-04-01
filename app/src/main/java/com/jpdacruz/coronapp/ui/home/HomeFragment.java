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
import com.jpdacruz.coronapp.MyApp;
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
    private TextView totalConfirmados, totalFallecidos, totalRecuperados, totalPaisesAfectados;
    private ProgressBar progressBar;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        iniciarComponentes(root);
        initViewModel();

        return root;
    }

    private void initViewModel() {

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<HomeEntity>() {
            @Override
            public void onChanged(HomeEntity homeEntity) {

                totalConfirmados.setText(homeEntity.getCases());
                totalFallecidos.setText(homeEntity.getDeaths());
                totalRecuperados.setText(homeEntity.getRecovered());
                totalPaisesAfectados.setText(homeEntity.getAffectedCountries());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void iniciarComponentes(View root) {

        Log.d(TAG,"INICIANDO COMPONENTES");

        totalConfirmados = root.findViewById(R.id.textViewConfirmadosNumber);
        totalFallecidos = root.findViewById(R.id.textViewFallecidosNumber);
        totalRecuperados = root.findViewById(R.id.textViewRecuperadosNumber);
        totalPaisesAfectados = root.findViewById(R.id.textViewCountriesAfectedNumber);

        progressBar = root.findViewById(R.id.progressBar);
    }
}
