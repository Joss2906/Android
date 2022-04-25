package com.example.intch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Configuracion extends AppCompatActivity implements View.OnClickListener {
    Button MCasa,ECasa,MHab,EHab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        findViewById(R.id.ModCasa).setOnClickListener(this);
        findViewById(R.id.ElimCasa).setOnClickListener(this);
        findViewById(R.id.ModHab).setOnClickListener(this);
        findViewById(R.id.ElimHab).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case (R.id.ModCasa):
                String token = ((EditText) findViewById(R.id.tok)).getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", token);
                Intent intent = new Intent(this, ModCasa.class);
                startActivity(intent);
                break;
            case (R.id.ElimCasa):
                String token1 = ((EditText) findViewById(R.id.tok)).getText().toString();
                SharedPreferences sharedPreferences1 = getSharedPreferences("token", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.putString("token", token1);
                intent = new Intent(this, ElimCasa.class);
                startActivity(intent);
                break;
            case (R.id.ModHab):
                String token2 = ((EditText) findViewById(R.id.tok)).getText().toString();
                SharedPreferences sharedPreferences2 = getSharedPreferences("token", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putString("token", token2);
                intent = new Intent(this, ModHab.class);
                startActivity(intent);
                break;
            case (R.id.ElimHab):
                String token3 = ((EditText) findViewById(R.id.tok)).getText().toString();
                SharedPreferences sharedPreferences3 = getSharedPreferences("token", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                editor3.putString("token", token3);
                intent = new Intent(this, ElimHab.class);
                startActivity(intent);
                break;
        }
    }
}
