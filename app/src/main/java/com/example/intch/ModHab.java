package com.example.intch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ModHab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_hab);

    }

    public void ModHab(View view) {
        String nom_hab=((EditText)findViewById(R.id.nomHab_mod)).getText().toString();
        Intent intent = new Intent(this, FormularioHabitacion.class);
        intent.putExtra("nombre",nom_hab);
        startActivity(intent);
    }
}

