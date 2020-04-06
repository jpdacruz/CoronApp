package com.jpdacruz.coronapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.constantes.Constantes;

public class EmailActivity extends AppCompatActivity {

    private Button btnEmail;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        iniciarComponentes();
    }

    private void iniciarComponentes() {

        btnEmail = findViewById(R.id.buttonEmailIngresar);
        editText = findViewById(R.id.editTextTextoAEnviar);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMail();
            }
        });
    }

    private void enviarMail() {

        String textoAenviar = editText.getText().toString();
        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constantes.MY_EMAIL});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,Constantes.EMAIL_SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT,textoAenviar);

        try{
            startActivity(Intent.createChooser(emailIntent, "Enviar via: "));
            finish();

        }catch (Exception e){
            //si no hay internet o no hay cliente de email
            Toast.makeText(getApplicationContext(),"Debe configurar una cuenta de email", Toast.LENGTH_LONG).show();
        }
    }
}
