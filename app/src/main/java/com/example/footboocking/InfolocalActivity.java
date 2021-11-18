package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class InfolocalActivity extends AppCompatActivity implements com.example.footboocking.adapterCanchas.OnItemClickListener {

    int id;
    String URL = null;

    RecyclerView recyclerView;
    adapterCanchas adapterCanchas;

    List<Product> canchaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infolocal);

        canchaList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewCanchas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        loadRequest();
    }

    private void loadRequest() {

        String id_canchas2;
        id_canchas2 = getIntent().getExtras().getString("ID");

        URL = "http://192.168.0.18:50/api/footbocking/get-numCanchas-by-id.php?id=" + id_canchas2;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                int idLocal = jsonObject.getInt("id_Local");
                                String disponible = jsonObject.getString("disponible");
                                String nombre = jsonObject.getString("nombre");
                                String image = jsonObject.getString("imagen");

                                Product product = new Product(id, idLocal, disponible, nombre, image);
                                canchaList.add(product);
                            }
                            adapterCanchas = new adapterCanchas(InfolocalActivity.this, canchaList);
                            recyclerView.setAdapter(adapterCanchas);
                            adapterCanchas.setOnItemClickListener(InfolocalActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InfolocalActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void OnItemClick(int position) {
        Intent edit = new Intent(this, formatoReserva.class);
        Product clickItem = canchaList.get(position);

        edit.putExtra("id_local",clickItem.getId());
        startActivity(edit);
    }
}