package com.example.footboocking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.app.Activity;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class RegistrarUsuario extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText nombreTx, apellidoTx, emailTx, contraseñaTx, telefonotx;
    Button button, buttonQr;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        nombreTx = findViewById(R.id.nombre);
        apellidoTx = findViewById(R.id.apellido);
        emailTx = findViewById(R.id.email);
        contraseñaTx = findViewById(R.id.Password);
        telefonotx = findViewById(R.id.editTextPhone);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        buttonQr = findViewById(R.id.buttonQr);
        button.setOnClickListener(this);
        buttonQr.setOnClickListener(this);
    }

    public void tomarFoto(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imageBitmap);
            System.out.println(""+ convert(imageBitmap));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    EditText nom=findViewById(R.id.nombre);
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con= DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/footbocking","roo","123456");
                        Statement stmt= con.createStatement();
                        //stmt.executeUpdate("INSERT INTO prueba VALUES(null, 'Juan','"+convert(imageBitmap)+"')");
                       stmt.executeUpdate("INSERT INTO usuario VALUES(NULL, '"+nombreTx.getText().toString()+"', '"+emailTx.getText().toString()+"', '"+telefonotx.getText().toString()+"', '"+apellidoTx.getText().toString()+"', '"+contraseñaTx.getText().toString()+"', '2', '"+convert(imageBitmap)+"')");

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                }
            }).start();
            Intent Irlogin = new Intent(this, logIn.class);
            Irlogin.putExtra("email", emailTx.getText().toString());
            startActivity(Irlogin);
        }

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getApplicationContext(), "Error de lectura", Toast.LENGTH_LONG).show();
            } else {
                String scanContent = result.getContents();
                StringTokenizer t = new StringTokenizer(scanContent, "*");
                String nombre = t.nextToken();
                String apellido = t.nextToken();
                String telefonoTx = t.nextToken();
                String email = t.nextToken();
                String contraseña = t.nextToken();
                nombreTx.setText("" + nombre);
                apellidoTx.setText("" + apellido);
                telefonotx.setText("" + telefonoTx);
                emailTx.setText("" + email);
                contraseñaTx.setText("" + contraseña);
                Toast.makeText(getApplicationContext(), "Captura de datos", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonQr) {
            IntentIntegrator integrador = new IntentIntegrator(RegistrarUsuario.this);
            integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrador.setPrompt("Lector QR");
            integrador.setCameraId(0);
            integrador.setBeepEnabled(true);
            integrador.setBarcodeImageEnabled(true);
            integrador.initiateScan();
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
