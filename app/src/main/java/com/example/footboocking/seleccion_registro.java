package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class seleccion_registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_registro);
    }

    public void IrTelefono(View view) {
        Intent IrTelefono = new Intent(this, activity_registrar_locales_canchas.class);
        startActivity(IrTelefono);
    }

    public void IrEmail(View view) {
        Intent IrEmail = new Intent(this, RegistrarUsuario.class);
        startActivity(IrEmail);
    }
}