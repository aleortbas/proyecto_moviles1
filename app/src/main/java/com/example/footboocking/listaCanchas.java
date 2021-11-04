package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class listaCanchas extends AppCompatActivity implements com.example.footboocking.adapter.OnItemClickListener {

    private static String id;
    String URL = null;
    String id_canchas;

    RecyclerView recyclerView;
    adapter adapter;

    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Consultar(listaCanchas.this).execute();
        setContentView(R.layout.activity_lista_canchas2);

        id = getIntent().getExtras().getString("ID");
        productList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadProducts();
    }

    private canchas consultar() throws JSONException, IOException {

        String usuario = getIntent().getExtras().getString("ID");

        String url = Constants.URL + "footbocking/get-canchas-by-id.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id_usuario", usuario.trim()));

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs);
        if (json != null) {
            JSONObject object = new JSONObject(json);
            JSONArray json_array = object.optJSONArray("canchas");
            if (json_array.length() > 0) {
                canchas multa = new canchas(json_array.getJSONObject(0));
                id_canchas = multa.getId();
                return multa;
            }
            return null;
        }
        return null;
    }

    public void AddCancha(View view) {
        Intent Add = new Intent(this, activity_registrar_locales_canchas.class);
        Add.putExtra("ID", id);
        Add.putExtra("Id_canchas", id_canchas);
        startActivity(Add);
    }


    class Consultar extends AsyncTask<String, String, String> {
        private Activity context;

        Consultar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            try {
                final canchas multa = consultar();
                if (multa != null)
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void loadProducts() {

        String id_canchas2;
        id_canchas2 = getIntent().getExtras().getString("Id_canchas");

        if(id_canchas == null) {
            URL = "http://192.168.0.18:50/api/footbocking/get-numCanchas-by-id.php?id=" + id_canchas2;
        }else{
            URL = "http://192.168.0.18:50/api/footbocking/get-numCanchas-by-id.php?id=" + id_canchas;
        }
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
                                productList.add(product);
                            }
                            adapter = new adapter(listaCanchas.this, productList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(listaCanchas.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(listaCanchas.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void OnItemClick(int position) {
        Intent edit = new Intent(this, updateCancha.class);
        Product clickItem = productList.get(position);

        edit.putExtra("id",clickItem.getId());
        edit.putExtra("id_local",clickItem.getIdLocal());
        edit.putExtra("nombre",clickItem.getNombre());
        edit.putExtra("disponible",clickItem.getDisponible());
        edit.putExtra("imagen",clickItem.getImage());
        startActivity(edit);
    }

}