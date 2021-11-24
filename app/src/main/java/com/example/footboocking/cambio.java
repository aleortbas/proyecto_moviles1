package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class cambio extends AppCompatActivity {

    int id, identi;
    String nombre;
    EditText n, idtxt, identificacion;
    Button cambio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        identi = intent.getIntExtra("cedula",0);
        nombre = intent.getStringExtra("nombre");

        n=findViewById(R.id.id_unico_local);
        idtxt=findViewById(R.id.cedula);
        identificacion=findViewById(R.id.nombre_user);
        cambio=findViewById(R.id.buttonAdd);

        n.setText(nombre);
        identificacion.setText(identi+"");
        idtxt.setText(id+"");

        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Cambiar(cambio.this).execute();
            }
        });

    }

    private boolean cambiar() {

        String url = Constants.URL + "footbocking/cambio_rol.php";
        String idU = Integer.toString(id);

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(7);
        nameValuePairs.add(new BasicNameValuePair("idUser", idU.toString().trim()));


        boolean response = APIHandler.POST(url, nameValuePairs); // enviamos los datos por POST al Webservice PHP
        return response;
    }

    class Cambiar extends AsyncTask<String, String, String> {
        private Activity context;

        Cambiar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (cambiar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Rol nuevo asignado", Toast.LENGTH_LONG).show();
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Ocurrio un erro", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}