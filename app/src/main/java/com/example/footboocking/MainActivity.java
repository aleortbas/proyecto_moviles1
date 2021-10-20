package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void IrRegistrar(View view) {
        Intent IrRegistrar = new Intent(this, seleccion_registro.class);
        startActivity(IrRegistrar);
    }

    public void LogIn(View view) {
        Intent IrRegistrar = new Intent(this, logIn.class);
        startActivity(IrRegistrar);
    }
}