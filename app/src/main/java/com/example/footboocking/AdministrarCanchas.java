package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministrarCanchas extends AppCompatActivity {

    String id;
    ImageView imagen;
    TextView idCancha, nombre, precio, idUser, numeroCanchas, direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Consultar(AdministrarCanchas.this).execute();
        setContentView(R.layout.activity_administrar_canchas);

        idCancha = findViewById(R.id.idLocal);
        nombre = findViewById(R.id.nombre);
        precio = findViewById(R.id.precio);
        idUser = findViewById(R.id.idUser);
        numeroCanchas = findViewById(R.id.numero_canchas);
        direccion = findViewById(R.id.direccion);

        id = getIntent().getExtras().getString("ID");

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
                return multa;
            }
            return null;
        }
        return null;
    }

    public void agregarCancha(View view) {
        Intent IrLoca = new Intent(this, activity_registrar_locales_canchas.class);
        IrLoca.putExtra("ID",id);
        startActivity(IrLoca);
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
                            idUser.setText("ID cancha: " + multa.getId());
                            nombre.setText(multa.getNombre());
                            numeroCanchas.setText(multa.getNumero_canchas());
                            precio.setText(multa.getPrecio());
                            direccion.setText(multa.getDireccion());
                        }
                    });
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nombre.setText("Todavia no registrar los datos de tu negocio, por favor ");
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

}