package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class consultaReservasAdmin extends AppCompatActivity {

    String nombre,hora, hora_final, fecha, resultado="", dato, id;
    int id_cancha;
    Spinner lista;
    Button boton, boton2, boton3, boton4, boton5, boton6;
    ArrayList<String> opciones2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_reservas_admin);

        id = getIntent().getExtras().getString("ID");

        boton = findViewById(R.id.button3);
        boton2 = findViewById(R.id.button4);
        boton3 = findViewById(R.id.button5);
        boton4 = findViewById(R.id.button6);
        boton5 = findViewById(R.id.button7);
        boton6 = findViewById(R.id.button8);
        lista = findViewById(R.id.spinner);
        opciones2 = new ArrayList<String>();
        opciones2.add("Consulte sus reservas");

        boton.setText(id);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/footbocking","root","123456");
                    String sql2 = "SELECT * FROM `reservas` WHERE id_cancha='"+id+"'";
                    Statement stmt2 = con.createStatement();
                    ResultSet result2 = stmt2.executeQuery(sql2);
                    while(result2.next()){

                        nombre = result2.getString("nombre");
                        hora = result2.getString("hora_inicio");
                        hora_final = result2.getString("hora_final");
                        fecha = result2.getString("fecha");

                        resultado = String.valueOf(",")+ "Nombre: " + nombre + ",Hora inicial: " + hora + ", Hora final: " + hora_final+ ", ID cancha: " + id_cancha + ", Fecha: " + fecha + ", ID reserva: " + id + ", Fecha: " + fecha + "";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                opciones2.add(resultado);
                            }
                        });
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    con.close();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }


        }).start();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, opciones2);
        lista.setAdapter(adapter2);

    }
}