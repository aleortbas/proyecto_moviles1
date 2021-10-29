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

public class agregarInfoCanchas extends AppCompatActivity {

    String id, id_usuario;
    Button add;
    EditText idLocal, estado, nombreCancha, imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_info_canchas);

        id = getIntent().getExtras().getString("ID");
        id_usuario = getIntent().getExtras().getString("id_usuario");

        idLocal = findViewById(R.id.id_unico_local);
        estado = findViewById(R.id.estado);
        nombreCancha = findViewById(R.id.nombre_Cancha);
        imagen = findViewById(R.id.imagen_Link);
        add = findViewById(R.id.buttonAdd);

        idLocal.setText(id);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!idLocal.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!estado.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!nombreCancha.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!imagen.getText().toString().trim().equalsIgnoreCase(""))){
                    new Insertar(agregarInfoCanchas.this).execute();
                } else {

                    Toast.makeText(agregarInfoCanchas.this, "Hay informacion por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean insertar() {

        String url = Constants.URL + "footbocking/add_num_cancha.php";

        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(7); // tamaño del array

        nameValuePairs.add(new BasicNameValuePair("id_local", idLocal.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("disponible", estado.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("nombre", nombreCancha.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("imagen", imagen.getText().toString().trim()));

        boolean response = APIHandler.POST(url, nameValuePairs);

        Intent Irlogin = new Intent(this,listaCanchas.class);
        Irlogin.putExtra("ID", idLocal.getText().toString());
        Irlogin.putExtra("id_usuario", id_usuario);
        startActivity(Irlogin);

        return response;

    }


    class Insertar extends AsyncTask<String, String, String> {
        private Activity context;

        Insertar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (insertar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Cancha creada", Toast.LENGTH_LONG).show();
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Cancha no creada", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}