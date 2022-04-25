package com.example.intch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Fragments.InicioFragment;
import SingletonRequest.SingletonRequest;

public class  RegistroHabitaciones extends AppCompatActivity implements View.OnClickListener {
    TextView linkhab;
    Button agregarhab;
    CheckBox cocina, bano, habprin, hab, sala, comedor;
    String url = "http://54.90.119.130/api/hab/insertar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_habitaciones);
        Objects.requireNonNull(getSupportActionBar()).hide();
        cocina = findViewById(R.id.checkcocina);
        bano = findViewById(R.id.checkbano);
        habprin = findViewById(R.id.checkprincipal);
        hab = findViewById(R.id.checkhab);
        sala = findViewById(R.id.checksala);
        comedor = findViewById(R.id.checkcomedor);
        agregarhab = findViewById(R.id.btnanadirhab);
        agregarhab.setOnClickListener(this);
        linkhab = findViewById(R.id.linkagregarhabs);
        linkhab.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnanadirhab) {
            if (cocina.isChecked()) {
                cocina.setText("Cocina");
            }
            if (bano.isChecked()) {
                bano.setText("Baño");
            }
            if (habprin.isChecked()) {
                habprin.setText("Habitación Principal");
            }
            if (hab.isChecked()) {
                hab.setText("Habitación");
            }
            if (sala.isChecked()) {
                sala.setText("Sala");
            }
            if (comedor.isChecked()) {
                comedor.setText("Comedor");
            }
            JSONObject jsonBody = new JSONObject();
            try {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject1 = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();
                JSONObject jsonObject3 = new JSONObject();
                JSONObject jsonObject4 = new JSONObject();
                JSONObject jsonObject5 = new JSONObject();

                try {
                    jsonObject.put("nombre_habitacion", cocina.getText().toString());
                    jsonObject1.put("nombre_habitacion", bano.getText().toString());
                    jsonObject2.put("nombre_habitacion", hab.getText().toString());
                    jsonObject3.put("nombre_habitacion", sala.getText().toString());
                    jsonObject4.put("nombre_habitacion", comedor.getText().toString());
                    jsonObject5.put("nombre_habitacion", habprin.getText().toString());
                    jsonArray.put(jsonObject);
                    jsonArray.put(jsonObject1);
                    jsonArray.put(jsonObject2);
                    jsonArray.put(jsonObject3);
                    jsonArray.put(jsonObject4);
                    jsonArray.put(jsonObject5);
                } catch (Exception e) {
                    Toast.makeText(this, "Error al enviar los datos", Toast.LENGTH_SHORT).show();
                }
                JsonArrayRequest carta = new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse( JSONArray response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistroHabitaciones.this, Inicio.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        SharedPreferences sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "");
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };
                SingletonRequest.getInstance(getApplicationContext()).addToRequesQue(carta);
            } catch (Exception e) {
                Toast.makeText(this, "Error al enviar los datos", Toast.LENGTH_SHORT).show();
            }
        }else if (view.getId() == R.id.linkagregarhabs) {
            Intent intent = new Intent(RegistroHabitaciones.this, RegistroHabitacion.class);
            startActivity(intent);
        }
    }
}















/*  if (cocina.isChecked()) {
                cocina.setText("Cocina");
                try {
                    jsonBody.put("nombre_habitacion", cocina.getText());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                cocina.setChecked(false);
            }
            if (bano.isChecked()) {
                bano.setText("Baño");
                try {
                    jsonBody.put("nombre_habitacion", bano.getText());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                bano.setChecked(false);
            }
            if (habprin.isChecked()) {
                habprin.setText("Habitación Principal");
                try {
                    jsonBody.put("nombre_habitacion", habprin.getText());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                habprin.setChecked(false);
            }
            if (hab.isChecked()) {
                hab.setText("Habitación");
                try {
                    jsonBody.put("nombre_habitacion", hab.getText());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                hab.setChecked(false);
            }
            if (sala.isChecked()) {
                sala.setText("Sala");
                try {
                    jsonBody.put("nombre_habitacion", sala.getText());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sala.setChecked(false);
            }
            if (comedor.isChecked()) {
                comedor.setText("Comedor");
                try {
                    jsonBody.put("nombre_habitacion", comedor.getText());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                comedor.setChecked(false);
            }*/



