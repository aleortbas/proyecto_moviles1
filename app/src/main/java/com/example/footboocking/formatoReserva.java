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

public class formatoReserva extends AppCompatActivity {

    EditText idCancha, nombreReserva, fecha, horaInicio, horaFinal;
    Button registrar, atras, consultar;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formato_reserva);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_local", 0);

        idCancha = findViewById(R.id.idCanchaHora);
        nombreReserva = findViewById(R.id.nombreReserva);
        fecha = findViewById(R.id.fecha);
        horaInicio = findViewById(R.id.horaInicio);
        horaFinal = findViewById(R.id.horaFinal);

        registrar = findViewById(R.id.registrar);
        atras = findViewById(R.id.consultar);
        consultar = findViewById(R.id.consultar);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Consultar(formatoReserva.this).execute();
            }
        });
        idCancha.setText(""+id);
    }

    private horas consultar() throws JSONException, IOException {

        String url = Constants.URL + "footbocking/consultar.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(2);// array
        nameValuePairs.add(new BasicNameValuePair("id", idCancha.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("hora", horaInicio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("hora_final", horaFinal.getText().toString().trim()));

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs);
        if (json != null) {
            JSONObject object = new JSONObject(json);
            JSONArray json_array = object.optJSONArray("hora");
            if (json_array.length() > 0) {
                horas multa = new horas(json_array.getJSONObject(0));
                return multa;
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
                final horas multa = consultar();
                if (multa != null)
                    if(multa.getEstado().equals("1")) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Horario disponible", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Horario no disponible", Toast.LENGTH_LONG).show();
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