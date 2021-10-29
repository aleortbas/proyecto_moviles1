package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class updateCancha extends AppCompatActivity {


    TextView test2;
    int id,id_local;
    String nombre, disponible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cancha);

        Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        disponible = intent.getStringExtra("disponible");
        id = intent.getIntExtra("id",0);
        id_local = intent.getIntExtra("id_local",0);

        test2 = findViewById(R.id.textView8);

        //test2.setText(disponible+ "");
    }
}