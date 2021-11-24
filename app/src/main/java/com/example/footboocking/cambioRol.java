package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class cambioRol extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    EditText nombre,tipoId,numeroId,camaraComercio;
    String nombreTxt, tipoIdTxt, numeroIdTxt, comercioTxt,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_rol);

        id = getIntent().getExtras().getString("id");
        nombre = findViewById(R.id.nombreSol);
        tipoId = findViewById(R.id.tipoId);
        numeroId = findViewById(R.id.numeroId);
        camaraComercio = findViewById(R.id.camaraComercio);
        imageView = findViewById(R.id.userImg);

        nombreTxt = getIntent().getExtras().getString("nombre");
        nombre.setText(nombreTxt);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/footbocking","root","123456");
                    Statement stmt= con.createStatement();
                    ResultSet rs=stmt.executeQuery("SELECT * FROM usuario WHERE id='"+id+"' ");

                    while(rs.next()){

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        }).start();

    }

    public void tomarFoto(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imageBitmap);
            System.out.println(""+ convert(imageBitmap));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    EditText nom=findViewById(R.id.nombre);
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con= DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/footbocking","root","123456");
                        Statement stmt= con.createStatement();
                        //stmt.executeUpdate("INSERT INTO prueba VALUES(null, 'Juan','"+convert(imageBitmap)+"')");
                        stmt.executeUpdate("INSERT INTO solicitud VALUES(NULL, '"+nombre.getText().toString()+"', '"+tipoId.getText().toString()+"', '"+numeroId.getText().toString()+"','"+camaraComercio.getText().toString()+"','"+id+"', '1', '"+convert(imageBitmap)+"') ");


                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                }
            }).start();


        }
    }
    public static Bitmap convert(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public void consultaReservas(View view) {
        Intent consulta = new Intent(this, consultaReservas.class);
        startActivity(consulta);
    }

    public void solicitud(View view) {
        Intent consulta = new Intent(this, cambioRol.class);
        consulta.putExtra("id",id);
        startActivity(consulta);
    }

    public void atras(View view) {
        Intent consulta = new Intent(this, updateUser.class);
        consulta.putExtra("ID",id);
        startActivity(consulta);
    }
}