package Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncInfo;
import android.os.Bundle;

import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.intch.DetalleHabitacion;
import com.example.intch.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Adapters.AdaptadorCasas;
import Adapters.AdaptadorHabitaciones;
import Models.Adafruit;
import Models.AdafruitPuerta;
import Models.Casa;
import Models.CasasDueno;
import Models.Habitacion;
import Models.HabitacionesCasa;
import Models.Sensor;
import SingletonRequest.SingletonRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    Spinner spinner;
    Button abrir, cerrar;
    String url = "http://54.90.119.130/api/det_hab/habitacionesCasa";
    String url2 = "http://54.90.119.130/api/casas/casasDueno";
    String urlabrir = "http://54.90.119.130/api/Abrir/acceso";
    String urlcerrar = "http://54.90.119.130/api/Cerrar/acceso";
    TextView textodepuerta;
    String stringcasa;
    int idcasa;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        abrir = view.findViewById(R.id.btnabrir);
        cerrar = view.findViewById(R.id.btncerrar);
        textodepuerta = view.findViewById(R.id.txtpuertastatus);
        abrir.setOnClickListener(this);
        cerrar.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerhab);
        spinner = view.findViewById(R.id.spinner);
        ListarCasas();
        String Hab;
        String IdHab;
        Intent intent = getActivity().getIntent();
        Hab = intent.getStringExtra("habitacion");
        IdHab = intent.getStringExtra("idhabitacion");
        return view;
    }
    public void ListarCasas() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("correo", "");
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(new JSONObject().put("email", email));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url2, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                String json = response.toString();
                Type listType = new TypeToken<List<CasasDueno>>() {
                }.getType();
                List<CasasDueno> casas = gson.fromJson(json, listType);
                SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), casas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        SingletonRequest.getInstance(getContext()).addToRequesQue(jsonArrayRequest);
    }

    /*----------------------------------------------------------------------------------------------------------------------*/

    public void ListarHabitacion(){
    JSONArray jsonArrayhab = new JSONArray();
    try {
        jsonArrayhab.put(new JSONObject().put("idcasa", stringcasa));
    }
    catch (JSONException e) {
        e.printStackTrace();
    }
        JsonArrayRequest jsonArrayRequest2= new JsonArrayRequest(Request.Method.POST, url, jsonArrayhab, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                String json = response.toString();
                Type listType = new TypeToken<List<HabitacionesCasa>>() {
                }.getType();
                List<HabitacionesCasa> habitaciones = gson.fromJson(json, listType);
                AdaptadorHabitaciones adaptadorHabitaciones = new AdaptadorHabitaciones(habitaciones);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adaptadorHabitaciones);
                recyclerView.setHasFixedSize(true);
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
            };
                      SingletonRequest.getInstance(getContext()).addToRequesQue(jsonArrayRequest2);
        }

        @Override
        public void onClick(View view) {
                if (view.getId() == R.id.btnabrir) {
                    JsonObjectRequest jsonBody1 = new JsonObjectRequest(Request.Method.POST, urlabrir, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Gson gson = new Gson();
                                AdafruitPuerta sensor = gson.fromJson(response.toString(), AdafruitPuerta.class);
                                String valor = sensor.getValue();
                                if (valor.equals("1")) {
                                   textodepuerta.setText("Abierta");
                                }
                                else {
                                    textodepuerta.setText("Cerrada");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
                            String token = sharedPreferences.getString("token", "");
                            headers.put("Authorization", "Bearer " + token);
                            return headers;
                        }

                    };
                    SingletonRequest.getInstance(getContext()).addToRequesQue(jsonBody1);
                }  else if (view.getId() == R.id.btncerrar){
                    JsonObjectRequest jsonBody2 = new JsonObjectRequest(Request.Method.POST, urlcerrar, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Gson gson = new Gson();
                                AdafruitPuerta sensor = gson.fromJson(response.toString(), AdafruitPuerta.class);
                                String valor = sensor.getValue();
                                if (valor.equals("0")) {
                                    textodepuerta.setText("Cerrada");
                                }
                                else {
                                    textodepuerta.setText("Abierta");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
                            String token = sharedPreferences.getString("token", "");
                            headers.put("Authorization", "Bearer " + token);
                            return headers;
                        }

                    };
                    SingletonRequest.getInstance(getContext()).addToRequesQue(jsonBody2);

            }
        }

}

   /* public void ListarHabitacion(){
            JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Type tipolistahab = new TypeToken<List<Habitacion>>() {
                    }.getType();
                    Gson gson = new Gson();
                    String data = response.toString();
                    JSONObject respuesta = null;
                    try {
                        respuesta = response.getJSONObject(respuesta.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    List<Habitacion> habitaciones = gson.fromJson(respuesta.toString(), tipolistahab);
                    Habitacion habitacion = gson.fromJson(data, Habitacion.class);
                    AdaptadorHabitaciones adapter = new AdaptadorHabitaciones( habitaciones);
                    recyclerView.setAdapter(adapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "");
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };
            SingletonRequest.getInstance(getContext()).addToRequesQue(solicitud);

        }

    }
*/