package com.jpdacruz.coronapp.ui.fragments.paises;

import android.content.Intent;
import android.os.Bundle;
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

import com.jpdacruz.coronapp.ui.activities.CountryActivity;
import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.clases.CountryEntity;
import com.jpdacruz.coronapp.db.clases.HomeEntity;
import com.jpdacruz.coronapp.ui.fragments.home.HomeViewModel;


import java.util.List;

public class NotificationsFragment extends Fragment {

    //vars
    private static final String TAG = "NotificationsFragment";

    private List<CountryEntity> countryEntityList;
    private NotificationsViewModel notificationsViewModel;
    private HomeViewModel homeViewModel;

    //widgets
    private TextView mCountry, mCases, mDeath;
    private RecyclerView recyclerViewCountries;
    private ProgressBar progressBar;
    private CountriesAdapter adapter;
    private TextView paisesAfectados;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_paises, container, false);

        iniciarComponentes(root);
        iniciarAdapter();
        iniciarViewModel();
        setPaisesAfectados();
        iniciarListenerRecycler();

        return root;
    }

    private void iniciarComponentes(View root) {

        recyclerViewCountries = root.findViewById(R.id.recyclerPaises);
        progressBar = root.findViewById(R.id.progressBarPaises);
        mCountry = root.findViewById(R.id.textViewPais);
        mCases = root.findViewById(R.id.textViewCountriesConfirmadosNumber);
        mDeath = root.findViewById(R.id.textViewCountriesFallecidosNumber);
        paisesAfectados = root.findViewById(R.id.textViewPaisesAfectados);
    }

    private void setPaisesAfectados() {

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<HomeEntity>() {
            @Override
            public void onChanged(HomeEntity homeEntity) {

                paisesAfectados.setText(String.format("Paises afectados: %s", homeEntity.getAffectedCountries()));
            }
        });
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

    private void iniciarListenerRecycler() {

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CountryEntity countryEntity =countryEntityList.get(recyclerViewCountries.getChildAdapterPosition(v));

                Intent intent = new Intent(getActivity(), CountryActivity.class);
                intent.putExtra("info",countryEntity);
                startActivity(intent);
            }
        });
    }
}
