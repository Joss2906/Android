package com.example.intch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import SingletonRequest.SingletonRequest;

public class FormularioHabitacion extends AppCompatActivity implements View.OnClickListener {
    Button btnModHab;
    EditText NuevoNomHab;
    TextView NombreAnt;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_habitacion);
        btnModHab=findViewById(R.id.btnModHab);
        NombreAnt=findViewById(R.id.NomHab);
        NombreAnt.setText(getIntent().getExtras().getString("nombre"));
        NuevoNomHab=findViewById(R.id.NuevoNomHab);
        btnModHab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, Configuracion.class);
        startActivity(intent);
        url="http://54.90.119.130/api/hab/modificar";
        String nNombre= NuevoNomHab.getText().toString();
        String NombreAnt= String.valueOf(getIntent().getExtras().getString("nombre"));
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nombre_anterior",NombreAnt);
            jsonBody.put("nuevo_nombre_hab",nNombre);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest modicarCasa=new JsonObjectRequest(Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(FormularioHabitacion.this, "Cambios realizados con exito", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FormularioHabitacion.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<String,String>();
                SharedPreferences sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token","");
                headers.put("Authorization","Bearer"+token);
                return headers;
            }
        };
        SingletonRequest.getInstance(this).addToRequesQue(modicarCasa);
    }
}
