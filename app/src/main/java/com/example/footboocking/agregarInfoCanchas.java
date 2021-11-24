package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class agregarInfoCanchas extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String id, id_usuario;
    Button add;
    ImageView imageView;
    EditText idLocal, estado, nombreCancha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_info_canchas);

        id = getIntent().getExtras().getString("Id_canchas");
        id_usuario = getIntent().getExtras().getString("ID");

        idLocal = findViewById(R.id.id_unico_local);
        estado = findViewById(R.id.estado);
        nombreCancha = findViewById(R.id.nombre_Cancha);
        add = findViewById(R.id.buttonAdd);
        imageView = findViewById(R.id.imageView);

        idLocal.setText(id);

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
                        stmt.executeUpdate("INSERT   INTO numero_canchas VALUES(NULL, '"+estado.getText().toString()+"','"+nombreCancha.getText().toString()+"','"+idLocal.getText().toString()+"','"+convert(imageBitmap)+"')");

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
}