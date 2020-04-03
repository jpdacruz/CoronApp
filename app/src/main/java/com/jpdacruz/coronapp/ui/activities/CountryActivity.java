package com.jpdacruz.coronapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.clases.CountryEntity;

public class CountryActivity extends AppCompatActivity {

    private TextView mPais, mCasos, mCasosHoy, mDeath,mDeathHoy, mSanados
            , mCasosActivos, mCasosCriticos, mCasosPorMillon, mDeathbyMillions, mUpdate;

    private ImageView imageViewFlag;

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
        imageViewFlag = findViewById(R.id.imageViewFlagCountry);
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
        mCasos.setText(countryEntity.getCases());
        mCasosHoy.setText(countryEntity.getTodayCases());
        mDeath.setText(countryEntity.getDeaths());
        mDeathHoy.setText(countryEntity.getTodayDeaths());
        mSanados.setText(countryEntity.getRecovered());
        mCasosActivos.setText(countryEntity.getActive());
        mCasosCriticos.setText(countryEntity.getCritical());
        mCasosPorMillon.setText(countryEntity.getCasesPerOneMillion());
        mDeathbyMillions.setText(countryEntity.getDeathsPerOneMillion());
        mUpdate.setText(String.format("Actualizado al\n%s", countryEntity.getUpdated()));

        Glide.with(getApplicationContext())
                .load(countryEntity
                        .getFlag())
                .apply(new RequestOptions().override(300,200))
                .into(imageViewFlag);
    }
}
