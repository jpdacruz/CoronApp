package com.jpdacruz.coronapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.clases.CountryEntity;

public class CountryActivity extends AppCompatActivity {

    private TextView mPais, mCasos, mCasosHoy, mDeath,mDeathHoy, mSanados
            , mCasosActivos, mCasosCriticos, mCasosPorMillon, mDeathbyMillions, mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        iniciarComponentes();
        comprobarBundle();
    }

    private void iniciarComponentes() {

        mPais = findViewById(R.id.textViewPais);
        mCasos = findViewById(R.id.textViewCasos);
        mCasosHoy= findViewById(R.id.textViewCasosHoy);
        mDeath = findViewById(R.id.textViewFallecidos);
        mDeathHoy = findViewById(R.id.textViewFallecidosHoy);
        mSanados = findViewById(R.id.textViewSanados);
        mCasosActivos = findViewById(R.id.textViewActivos);
        mCasosCriticos = findViewById(R.id.textViewCriticos);
        mCasosPorMillon = findViewById(R.id.textViewCasosMillon);
        mDeathbyMillions = findViewById(R.id.textViewFallecidosMillon);
        mUpdate = findViewById(R.id.textViewLastUpdateCountry);
    }

    private void comprobarBundle() {

        Bundle bundle = getIntent().getExtras();

        CountryEntity countryEntity = null;

        if(bundle != null){

            countryEntity = (CountryEntity) bundle.getSerializable("info");

            asignarValores(countryEntity);
        }
    }

    private void asignarValores(CountryEntity countryEntity) {

        mPais.setText(countryEntity.getCountry());
        mCasos.setText(String.format("Casos: %s", countryEntity.getCases()));
        mCasosHoy.setText(String.format("Casos hoy: %s", countryEntity.getTodayCases()));
        mDeath.setText(String.format("Fallecidos: %s", countryEntity.getDeaths()));
        mDeathHoy.setText(String.format("Fallecidos hoy: %s", countryEntity.getTodayDeaths()));
        mSanados.setText(String.format("Sanados: %s", countryEntity.getRecovered()));
        mCasosActivos.setText(String.format("Casos activos: %s", countryEntity.getActive()));
        mCasosCriticos.setText(String.format("Casos Criticos: %s", countryEntity.getCritical()));
        mCasosPorMillon.setText(String.format("Casos por millón habitantes: %s", countryEntity.getCasesPerOneMillion()));
        mDeathbyMillions.setText(String.format("Fallecidos por millón habitantes: %s", countryEntity.getDeathsPerOneMillion()));
        mUpdate.setText(String.format("Ultima actualización\n%s", countryEntity.getUpdated()));
    }
}
