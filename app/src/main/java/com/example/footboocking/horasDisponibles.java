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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class horasDisponibles extends AppCompatActivity {

    EditText idCancha, nombreReserva, fecha, horaInicio, horaFinal;
    Button registrar,atras;
    int horas, minutos;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas_disponibles);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        horaInicio = findViewById(R.id.horaInicio);
        horaFinal = findViewById(R.id.horaFinal);
        idCancha=findViewById(R.id.idCanchaHora);
        registrar=findViewById(R.id.registrar);
        atras=findViewById(R.id.atras);

        idCancha.setText(id);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Insertar(horasDisponibles.this).execute();
            }
        });
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

    private boolean insertar() {

        String url = Constants.URL + "footbocking/addHoras.php";

        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(4); // tamaño del array

        nameValuePairs.add(new BasicNameValuePair("hora", horaInicio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("hora_final", horaFinal.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("estado", "1"));
        nameValuePairs.add(new BasicNameValuePair("id", idCancha.getText().toString().trim()));

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
                        Toast.makeText(context, "Horario creado", Toast.LENGTH_LONG).show();

                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Horario no creado", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}