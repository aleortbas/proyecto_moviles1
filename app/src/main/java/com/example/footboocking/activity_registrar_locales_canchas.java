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

public class activity_registrar_locales_canchas extends AppCompatActivity {

    EditText nombreLocal,  direccion, numeroCanchas, precio;
    Button button, administrar;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_loacales_canchas);

        nombreLocal = findViewById(R.id.nombreLocal);
        direccion = findViewById(R.id.direccion);
        numeroCanchas = findViewById(R.id.NumeroCanchas);
        precio = findViewById(R.id.precioHora);

        id = getIntent().getStringExtra("ID");

        button = findViewById(R.id.buttonLocal);
        administrar = findViewById(R.id.buttonAdmin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!nombreLocal.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!direccion.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!numeroCanchas.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!precio.getText().toString().trim().equalsIgnoreCase(""))){
                    new Insertar(activity_registrar_locales_canchas.this).execute();
                } else {

                    Toast.makeText(activity_registrar_locales_canchas.this, "Hay informacion por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // CRUD from here --------
    private boolean insertar() {

        String url = Constants.URL + "footbocking/addLocal.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(8); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("nombre", nombreLocal.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("direccion", direccion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("numero_canchas", numeroCanchas.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("precio_hora", precio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("id_usuario", id.trim()));


        boolean response = APIHandler.POST(url, nameValuePairs);
        return response;
    }

    public void IrAdministrar(View view) {
        Intent IrLista = new Intent(this, AdministrarCanchas.class);
        IrLista.putExtra("ID",id);
        startActivity(IrLista);
    }
//----------Eventos del AsyncTask para los botones

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
                        Toast.makeText(context, "Usuario creado", Toast.LENGTH_LONG).show();
                        nombreLocal.setText("");
                        direccion.setText("");
                        numeroCanchas.setText("");
                        precio.setText("");
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Usuario no creado", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}