package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class RegistrarUsuario extends AppCompatActivity implements View.OnClickListener {

    EditText nombreTx, apellidoTx, emailTx, contraseñaTx, telefonotx;
    Button button, buttonQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        nombreTx = findViewById(R.id.nombre);
        apellidoTx = findViewById(R.id.apellido);
        emailTx = findViewById(R.id.email);
        contraseñaTx = findViewById(R.id.Password);
        telefonotx = findViewById(R.id.editTextPhone);
        button = findViewById(R.id.button);
        buttonQr = findViewById(R.id.buttonQr);
        button.setOnClickListener(this);
        buttonQr.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getApplicationContext(), "Error de lectura", Toast.LENGTH_LONG).show();
            } else {
                String scanContent = result.getContents();
                StringTokenizer t = new StringTokenizer(scanContent, "*");
                String nombre = t.nextToken();
                String apellido = t.nextToken();
                String telefonoTx = t.nextToken();
                String email = t.nextToken();
                String contraseña = t.nextToken();
                nombreTx.setText("" + nombre);
                apellidoTx.setText("" + apellido);
                telefonotx.setText("" + telefonoTx);
                emailTx.setText("" + email);
                contraseñaTx.setText("" + contraseña);
                Toast.makeText(getApplicationContext(), "Captura de datos", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonQr) {
            IntentIntegrator integrador = new IntentIntegrator(RegistrarUsuario.this);
            integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrador.setPrompt("Lector QR");
            integrador.setCameraId(0);
            integrador.setBeepEnabled(true);
            integrador.setBarcodeImageEnabled(true);
            integrador.initiateScan();
        }

        if (v.getId() == R.id.button) {
            if ((!nombreTx.getText().toString().trim().equalsIgnoreCase("")) ||
                    (!apellidoTx.getText().toString().trim().equalsIgnoreCase("")) ||
                    (!emailTx.getText().toString().trim().equalsIgnoreCase("")) ||
                    (!contraseñaTx.getText().toString().trim().equalsIgnoreCase(""))) {
                new Insertar(RegistrarUsuario.this).execute();
            } else {

                Toast.makeText(RegistrarUsuario.this, "Hay informacion por llenar", Toast.LENGTH_SHORT).show();

            }
        }
    }

    // CRUD from here --------
    private boolean insertar() {

        String url = Constants.URL + "footbocking/add.php";
        String rol = "2";

        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(7); // tamaño del array

        nameValuePairs.add(new BasicNameValuePair("nombre", nombreTx.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("email", emailTx.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono", telefonotx.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("clave", contraseñaTx.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("apellido", apellidoTx.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("rol", rol.trim()));

        boolean response = APIHandler.POST(url, nameValuePairs);

        Intent Irlogin = new Intent(this, logIn.class);
        Irlogin.putExtra("email", emailTx.getText().toString());
        startActivity(Irlogin);

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
                        telefonotx.setText("");
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