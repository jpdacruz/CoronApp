package com.jpdacruz.coronapp.ui.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.clases.HomeEntity;
import com.jpdacruz.coronapp.ui.activities.EmailActivity;

public class HomeFragment extends Fragment {

    //vars
    private static final String TAG = "HomeFragment";
    private Button btnEmail;
    //widgets
    private TextView totalConfirmados, totalFallecidos, totalRecuperados,lastUpdated;
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

    private void iniciarComponentes(View root) {

        Log.d(TAG,"INICIANDO COMPONENTES");

        totalConfirmados = root.findViewById(R.id.textViewConfirmadosNumber);
        totalFallecidos = root.findViewById(R.id.textViewFallecidosNumber);
        totalRecuperados = root.findViewById(R.id.textViewRecuperadosNumber);
        lastUpdated = root.findViewById(R.id.textViewLastUpdate);
        btnEmail = root.findViewById(R.id.buttonMail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarEmail();
            }
        });

        progressBar = root.findViewById(R.id.progressBar);
    }

    private void initViewModel() {

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<HomeEntity>() {
            @Override
            public void onChanged(HomeEntity homeEntity) {

                totalConfirmados.setText(homeEntity.getCases());
                totalFallecidos.setText(homeEntity.getDeaths());
                totalRecuperados.setText(homeEntity.getRecovered());
                lastUpdated.setText("Actualizado al\n" + homeEntity.getUpdated());
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void enviarEmail() {

        Intent intent = new Intent(getActivity(), EmailActivity.class);
        startActivity(intent);
    }
}
