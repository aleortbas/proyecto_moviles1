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
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class updateCancha extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    int id,id_local;
    String nombre, disponible, imagen, idLocal;
    TextView idBaseDatos,id_LocalDB;
    EditText disponibleTxt, nombreTxt;
    ImageView imageView;
    Button edit,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cancha);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        idBaseDatos = findViewById(R.id.idBaseDatos);
        id_LocalDB = findViewById(R.id.id_Local);
        disponibleTxt = findViewById(R.id.disponible);
        nombreTxt = findViewById(R.id.nombreCancha);
        edit = findViewById(R.id.buttonEdit);
        delete = findViewById(R.id.buttonDelete);
        imageView = findViewById(R.id.fieldImg);

        idBaseDatos.setText(id + "");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Eliminar(updateCancha.this).execute();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/footbocking","root","123456");
                    Statement stmt= con.createStatement();
                    ResultSet rs=stmt.executeQuery("SELECT * FROM numero_canchas WHERE id='"+id+"' ");

                    while(rs.next()){
                        imagen = rs.getString("foto");
                        nombre = rs.getString("nombre");
                        idLocal = rs.getString("id_Local");
                        disponible = rs.getString("disponible");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(convert(imagen));
                            nombreTxt.setText(nombre);
                            id_LocalDB.setText(idLocal);
                            disponibleTxt.setText(disponible);
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
                        stmt.executeUpdate("UPDATE numero_canchas SET disponible='"+disponibleTxt.getText().toString()+"',nombre='"+nombreTxt.getText().toString()+"' ,foto='"+convert(imageBitmap)+"' WHERE id='"+id+"'");

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

    private boolean eliminar() {

        String url = Constants.URL + "footbocking/delete_num_canchas.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("id", idBaseDatos.getText().toString().trim()));
        boolean response = APIHandler.POST(url, nameValuePairs); // Enviamos el id al webservices

        return response;
    }

    public void atras(View view) {
        Intent IrUser = new Intent(getApplicationContext(), listaCanchas.class);
        startActivity(IrUser);
    }

    class Eliminar extends AsyncTask<String, String, String> {
        private Activity context;

        Eliminar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (eliminar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Cacha eliminada, la proxima vez que se registre la informacion se actualizara", Toast.LENGTH_LONG).show();
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Cancha no eliminada", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

}