package com.jpdacruz.coronapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.clases.FrasesCelebres;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageViewFoto;
    private TextView frase, autor, contadorTv;
    private int contador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        cargarSharedPreferences();
        iniciarComponentes();
        listadoFrases();
    }

    private void cargarSharedPreferences() {

        SharedPreferences sharedPreferences = getSharedPreferences("frases", MODE_PRIVATE);

        contador = sharedPreferences.getInt("cont",0);

    }

    private void guardarSharedPreferences() {

        SharedPreferences sharedPreferences = getSharedPreferences("frases", MODE_PRIVATE);
        contador = contador + 1;
        if (contador == 10) contador = 0;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cont",contador);
        editor.commit();

    }

    private void listadoFrases() {

        ArrayList<FrasesCelebres> frasesCelebres = new ArrayList();

        frasesCelebres.add(new FrasesCelebres
                ("Mi hijo desarrolló una erupción cutánea en la cara que, rápidamente, se extendió al resto del cuerpo. No podíamos dormir. Parecía tan enfermo que recuerdo la amarga experiencia como si la estuviese reviviendo.",
                        "Virólogo japonés\nDescubridor de la vacuna\ncontra la varicela.",
                        R.drawable.michiaki_takahashi));

        frasesCelebres.add(new FrasesCelebres
                ("Todo lo que toca este tipo se convierte en una vacuna, tenemos una deuda impagable con él.",
                        "Microbiólogo estadounidense\nEl padre de las 40 vacunas.",
                        R.drawable.maurice_hilleman));

        frasesCelebres.add(new FrasesCelebres
                ("¿ Que soy un heroe? Bueno, la gente necesita crear héroes. Mi obra no es exclusivamente mia, sino de centenares de personas.",
                        "Virólogo polaco \nDescubridor de la vacuna oral\ncontra la poliomelitis.",
                        R.drawable.albert_sabin));

        frasesCelebres.add(new FrasesCelebres
                ("Espero que algún día la práctica de contagiar la viruela vacuna a los seres humanos se extienda por el mundo -cuando llegue ese día, ya no habrá más viruela.",
                        "Médico inglés\nDescubridor de la vacuna contra la viruela\nPrimera vacuna fiable.",
                        R.drawable.edward_jenner));

        frasesCelebres.add(new FrasesCelebres
                ("La ciencia es el alma de la prosperidad de las naciones y la fuente de todo progreso.",
                        "Químico y bacteriólogo francés\nDescubridor de la vacuna\ncontra la rabia y la pasteurización.",
                        R.drawable.louis_pasteur));

        frasesCelebres.add(new FrasesCelebres
                ("Bueno, la vacuna es de la gente, diría yo. No hay patente. ¿Podría usted patentar el sol?",
                        "Médico y virólogo estadounidense\nPrimera vacuna Antigripal\n Vacuna Antipoliomielítica inerte.",
                        R.drawable.jonas_salk));

        frasesCelebres.add(new FrasesCelebres
                ("No puedo imaginar en toda la historia de la Medicina una muestra de filantropía tan enorme como ésta. Edward Jenner, creador de la primera vacuna, sobre la expedición de Francisco Javier Balmis.",
                        "Francisco Javier Balmis, Médico español\nPrimera expedición sanitaria\n internacional de la historia.",
                        R.drawable.balmis));

        frasesCelebres.add(new FrasesCelebres
                ("Yo soy de los que piensan como Nobel, que la humanidad extraerá más bien que mal de los nuevos descubrimientos.",
                        "Científica polaca\nPrimera mujer en ganar el premio Nobel.",
                        R.drawable.curie));

        frasesCelebres.add(new FrasesCelebres
                ("He visto hermanas de profesión, mujeres que ganaban dos o tres guineas a la semana, limpiando el piso de rodillas porque consideraban que las habitaciones no eran aptas para sus pacientes.",
                        "Enfermera inglesa\nPrecursora de la enfermería\nprofesional moderna",
                        R.drawable.nightingale));

        frasesCelebres.add(new FrasesCelebres
                ("Todavia quedaba en el pueblo un grupo de mujeres que podían ayudar. Llegamos a la larga hilera de soldados heridos apoyados unos con otros. Sufrían sin quejarse, y morían humildemente ,sin ruido.\nBatalla de Solferino 1859",
                        "Henry Dunant Empresario Suizo\nFundador de la Cruz Roja \ny la Media Luna Roja Internacional",
                        R.drawable.henry_dunant));

        imageViewFoto.setImageResource(frasesCelebres.get(contador).getFoto());
        frase.setText(frasesCelebres.get(contador).getFrase());
        autor.setText(frasesCelebres.get(contador).getAutor());
        contadorTv.setText(contador+1 +"/10");

    }

    private void iniciarComponentes() {

        imageViewFoto = findViewById(R.id.imageViewFotoMedico);
        frase = findViewById(R.id.textViewTexto);
        autor = findViewById(R.id.textViewAutor);
        contadorTv = findViewById(R.id.textViewContador);
    }

    public void continuar(View view) {

        guardarSharedPreferences();

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
