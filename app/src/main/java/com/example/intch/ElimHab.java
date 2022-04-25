package com.example.intch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class ElimHab extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elim_hab);
    }

    public void ElimHab(View view) {
        Intent intent = new Intent(this, Configuracion.class);
        startActivity(intent);
        String url="http://54.90.119.130/api/hab/borrar";
        String nombre=((EditText)findViewById(R.id.nomHab_elim)).getText().toString();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nombre_habitacion",nombre);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest modicarCasa=new JsonObjectRequest(Request.Method.DELETE, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(ElimHab.this, "Cambios realizados con exito", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ElimHab.this, error.toString(), Toast.LENGTH_SHORT).show();
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
