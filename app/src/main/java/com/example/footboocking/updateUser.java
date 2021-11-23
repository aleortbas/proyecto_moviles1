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
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class updateUser extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    EditText nom;
    EditText email;
    EditText clave;
    EditText telefono;
    TextView tvName, tvLastName;
    String id, foto, emailTxt, nombreTxt, apellidoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        id = getIntent().getExtras().getString("ID");
        imageView=findViewById(R.id.userImg);
        nom=findViewById(R.id.nombre);
        email=findViewById(R.id.email);
        clave=findViewById(R.id.Password);
        telefono=findViewById(R.id.telefono);
        tvName=findViewById(R.id.tv_name);
        tvLastName=findViewById(R.id.tv_lastName);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/footbocking","root","123456");
                    Statement stmt= con.createStatement();
                    ResultSet rs=stmt.executeQuery("SELECT * FROM usuario WHERE id='"+id+"' ");

                    while(rs.next()){
                        foto = rs.getString("foto");
                        emailTxt = rs.getString("email");
                        nombreTxt = rs.getString("nombre");
                        apellidoTxt = rs.getString("apellido");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(convert(foto));
                            email.setText(emailTxt);
                            tvName.setText(nombreTxt);
                            tvLastName.setText(apellidoTxt);
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
                        stmt.executeUpdate("UPDATE usuario SET foto='"+convert(imageBitmap)+"' WHERE id='"+id+"'");

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