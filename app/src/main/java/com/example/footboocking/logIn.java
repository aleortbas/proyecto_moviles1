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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

 public class logIn extends AppCompatActivity {

     String emailGet;
     EditText email, contraseña;
     Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Intent intent = getIntent();
        emailGet = intent.getStringExtra("email");

        email = findViewById(R.id.editTextTextEmailAddress);
        email.setText(emailGet);
        contraseña = findViewById(R.id.editTextTextPassword);

        button = findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Consultar(logIn.this).execute();
            }
        });
    }
     private usuario consultar() throws JSONException, IOException {

         String url = Constants.URL + "footbocking/get-by-id.php"; // Ruta

         //DATOS
         List<NameValuePair> nameValuePairs; // lista de datos
         nameValuePairs = new ArrayList<NameValuePair>(2);//definimos array
         nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString().trim()));
         nameValuePairs.add(new BasicNameValuePair("clave", contraseña.getText().toString().trim()));

         String json = APIHandler.POSTRESPONSE(url, nameValuePairs);
         if (json != null) {
             JSONObject object = new JSONObject(json);
             JSONArray json_array = object.optJSONArray("user");

             if (json_array.length() > 0) {
                 usuario user = new usuario(json_array.getJSONObject(0));
                 int rol = user.getRol();
                 String id = user.getId_user();
                 String email = user.getEmailDB();
                 String contraseñaDB = user.getContraseñaDB();
                 if(rol == 1) {
                     Intent IrAdmin = new Intent(this,Admin.class);
                     IrAdmin.putExtra("ID", id);
                     startActivity(IrAdmin);
                 }
                 if(rol == 2) {
                     Intent IrUser = new Intent(getApplicationContext(), MapsActivity.class);
                     IrUser.putExtra("ID", id);
                     startActivity(IrUser);
                 }
                 if(rol == 3) {
                     Intent IrAdmin = new Intent(this,listaCanchas.class);
                     IrAdmin.putExtra("ID", id);
                     startActivity(IrAdmin);
                 }
                 return user;
             }
             return null;
         }
         return null;
     }

     class Consultar extends AsyncTask<String, String, String> {
         private Activity context;

         Consultar(Activity context) {
             this.context = context;
         }

         protected String doInBackground(String... params) {
             try {
                 final usuario multa = consultar();
                 if (multa != null)
                     context.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(context, "Has ingresado correctamente", Toast.LENGTH_LONG).show();
                         }
                     });
                 else
                     context.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(context, "El usuario no se ecuentra registrado o los datos ingresados son erroneos", Toast.LENGTH_LONG).show();
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