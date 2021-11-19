package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class formatoReserva extends AppCompatActivity {

    EditText idCancha, nombreReserva, fecha, horaInicio, horaFinal;
    Button registrar, atras, consultar, timeButton;
    int id, horas, minutos;


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
        timeButton = findViewById(R.id.timeButton);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Consultar(formatoReserva.this).execute();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Insertar(formatoReserva.this).execute();
            }
        });

        idCancha.setText(""+id);
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horas = hourOfDay;
                minutos = minute;
                horaInicio.setText(String.format(Locale.getDefault(),"%02d:%02d", horas, minutos));
                horaFinal.setText(String.format(Locale.getDefault(),"%02d:%02d", horas+1, minutos));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, horas, minutos, true);

        timePickerDialog.setTitle("Hora selecionada");
        timePickerDialog.show();
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

    private boolean insertar() {

        String url = Constants.URL + "footbocking/addreserva.php";

        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(4); // tamaño del array

        nameValuePairs.add(new BasicNameValuePair("id", idCancha.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("nombre", nombreReserva.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("hora", horaInicio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("hora_final", horaFinal.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("fecha", fecha.getText().toString().trim()));

        boolean response = APIHandler.POST(url, nameValuePairs);

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
                        Toast.makeText(context, "Reserva creada", Toast.LENGTH_LONG).show();
                        horaInicio.setText("");
                        horaFinal.setText("");
                        fecha.setText("");
                        nombreReserva.setText("");
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Reserva no creado", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}