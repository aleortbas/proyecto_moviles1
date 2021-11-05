package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity implements com.example.footboocking.adapterRequest.OnItemClickListener{

    String URL = null;
    RecyclerView recyclerView;
    adapterRequest adapter;

    List<solicitud> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        productList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadRequest();
    }
    private void loadRequest() {

        URL = "http://192.168.0.18:50/api/footbocking/get-solicitud.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String nombre = jsonObject.getString("nombre_usuario");
                                String tipoID = jsonObject.getString("tipo_identificacion");
                                int numeroID = jsonObject.getInt("numero_identificacion");
                                String camaraComercio = jsonObject.getString("camara_comercio");
                                int idUsuario = jsonObject.getInt("id_usuario");
                                int estado = jsonObject.getInt("estado");
                                String foto = jsonObject.getString("foto");

                                solicitud product = new solicitud(id, numeroID, idUsuario, nombre, tipoID, camaraComercio, foto, estado);
                                productList.add(product);
                            }
                            adapter = new adapterRequest(Admin.this, productList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(Admin.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Admin.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void OnItemClick(int position) {

    }
}