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
    String id_cancha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formato_reserva);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_local",0);
        id_cancha = String.valueOf(id);

        idCancha = findViewById(R.id.idCancha);
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

        idCancha.setText(id+"");
    }

    private horario consultar() throws JSONException, IOException {

        String url = Constants.URL + "footbocking/consultar.php"; // Ruta

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(3);//definimos array
        nameValuePairs.add(new BasicNameValuePair("idCancha", id_cancha.trim()));
        nameValuePairs.add(new BasicNameValuePair("hora", horaInicio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("horaFinal", horaFinal.getText().toString().trim()));

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs);
        if (json != null) {
            JSONObject object = new JSONObject(json);
            JSONArray json_array = object.optJSONArray("horario");
            if (json_array.length() > 0) {
                horario multa = new horario(json_array.getJSONObject(0));
                return multa;
            }
            return null;
        }
        return null;
    }


    class Consultar extends AsyncTask<String, String, String>{
        private Activity context;

        Consultar(Activity context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                final horario hora = consultar();
                if (hora != null){
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(hora.getEstado()==1){
                                Toast.makeText(context, "Hora disponible", Toast.LENGTH_LONG).show();
                            }
                            if(hora.getEstado()==2){
                                Toast.makeText(context, "Hora no disponible", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}