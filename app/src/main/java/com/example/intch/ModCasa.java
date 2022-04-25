package com.example.intch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ModCasa extends AppCompatActivity {

    EditText nom_casa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_casa);
        nom_casa=findViewById(R.id.nomCasa_mod);

    }

    public void ModifCasa(View view) {
        Intent intent = new Intent(this, FormularioCasa.class);
        intent.putExtra("nombre",String.valueOf(nom_casa.getText()));
        startActivity(intent);

    }
}
