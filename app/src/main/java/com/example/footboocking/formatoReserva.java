package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class formatoReserva extends AppCompatActivity {

    EditText idLocal, idCancha, nombreReserva, fecha, horaInicio, horaFinal;
    Button button, atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formato_reserva);
    }
}