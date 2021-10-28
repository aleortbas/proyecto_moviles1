package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class updateCancha extends AppCompatActivity {


    TextView test, test2;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cancha);

        test = findViewById(R.id.textViewTitle);
        test2 = findViewById(R.id.textView8);

        test2.setText(test.getText().toString());
    }
}