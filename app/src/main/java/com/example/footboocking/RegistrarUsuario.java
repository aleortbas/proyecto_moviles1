package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

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

public class RegistrarUsuario extends AppCompatActivity {

    EditText nombreTx,apellidoTx,emailTx,contraseñaTx;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        nombreTx = findViewById(R.id.nombre);
        apellidoTx = findViewById(R.id.apellido);
        emailTx = findViewById(R.id.email);
        contraseñaTx = findViewById(R.id.Password);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!nombreTx.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!apellidoTx.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!emailTx.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!contraseñaTx.getText().toString().trim().equalsIgnoreCase(""))){
                    new Insertar(RegistrarUsuario.this).execute();
                } else {

                    Toast.makeText(RegistrarUsuario.this, "Hay informacion por llenar", Toast.LENGTH_SHORT).show();
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
        nameValuePairs.add(new BasicNameValuePair("nombre", nombreTx.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("email", emailTx.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("contraseña", contraseñaTx.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono", apellidoTx.getText().toString().trim()));

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
                        nombreTx.setText("");
                        apellidoTx.setText("");
                        emailTx.setText("");
                        contraseñaTx.setText("");
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