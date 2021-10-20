package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class activity_registrar_usuario_telefono extends AppCompatActivity {

    EditText telefono, nombre, apellido, contraseña;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario_telefono);

        nombre = findViewById(R.id.nombreTel);
        apellido = findViewById(R.id.apellidoTel);
        contraseña = findViewById(R.id.Passwordtel);
        telefono = findViewById(R.id.editTextPhone);

        button = findViewById(R.id.buttonTel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!nombre.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!apellido.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!telefono.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!contraseña.getText().toString().trim().equalsIgnoreCase(""))){
                    new Insertar(activity_registrar_usuario_telefono.this).execute();
                } else {

                    Toast.makeText(activity_registrar_usuario_telefono.this, "Hay informacion por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // CRUD from here --------
    private boolean insertar() {

        String url = Constants.URL + "footbocking/add.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(7); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("nombre", nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("apellido", apellido.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("clave", contraseña.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono", telefono.getText().toString().trim()));


        boolean response = APIHandler.POST(url, nameValuePairs);
        return response;
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
                        nombre.setText("");
                        apellido.setText("");
                        telefono.setText("");
                        contraseña.setText("");
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