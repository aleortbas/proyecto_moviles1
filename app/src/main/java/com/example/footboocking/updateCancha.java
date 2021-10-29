package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class updateCancha extends AppCompatActivity {

    int id,id_local;
    String nombre, disponible, imagen;
    TextView idBaseDatos,id_LocalDB;
    EditText disponibleTxt, nombreTxt, link;
    Button edit,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cancha);

        Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        disponible = intent.getStringExtra("disponible");
        imagen = intent.getStringExtra("imagen");
        id = intent.getIntExtra("id",0);
        id_local = intent.getIntExtra("id_local",0);

        idBaseDatos = findViewById(R.id.idBaseDatos);
        id_LocalDB = findViewById(R.id.id_Local);
        disponibleTxt = findViewById(R.id.disponible);
        nombreTxt = findViewById(R.id.nombreCancha);
        link = findViewById(R.id.imagenLink);
        edit = findViewById(R.id.buttonEdit);

        idBaseDatos.setText(id + "");
        id_LocalDB.setText(id_local + "");
        nombreTxt.setText(nombre);
        disponibleTxt.setText(disponible);
        link.setText(imagen);

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Modificar(updateCancha.this).execute();
            }
        });
    }

    private boolean modificar() {

        String url = Constants.URL + "footbocking/update.php";


        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(7);
        nameValuePairs.add(new BasicNameValuePair("id", idBaseDatos.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("id_local", id_LocalDB.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("disponible", disponibleTxt.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("nombre", nombreTxt.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("imagen", link.getText().toString().trim()));


        boolean response = APIHandler.POST(url, nameValuePairs); // enviamos los datos por POST al Webservice PHP
        return response;
    }

    class Modificar extends AsyncTask<String, String, String> {
        private Activity context;

        Modificar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (modificar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Multa modificada", Toast.LENGTH_LONG).show();
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Multa no encontrada", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}