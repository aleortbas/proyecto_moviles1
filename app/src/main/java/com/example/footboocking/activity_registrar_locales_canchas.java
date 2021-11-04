package com.example.footboocking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class activity_registrar_locales_canchas extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText nombreLocal,  direccion, numeroCanchas, precio, lat, lon;
    Button button, administrar;
    String id, id_canchas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_loacales_canchas);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        nombreLocal = findViewById(R.id.nombreLocal);
        direccion = findViewById(R.id.direccion);
        numeroCanchas = findViewById(R.id.NumeroCanchas);
        precio = findViewById(R.id.precioHora);

        id = getIntent().getStringExtra("ID");
        id_canchas = getIntent().getStringExtra("Id_canchas");

        button = findViewById(R.id.buttonLocal);
        administrar = findViewById(R.id.buttonAdmin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!nombreLocal.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!direccion.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!numeroCanchas.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!precio.getText().toString().trim().equalsIgnoreCase(""))){
                    new Insertar(activity_registrar_locales_canchas.this).execute();
                } else {

                    Toast.makeText(activity_registrar_locales_canchas.this, "Hay informacion por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // CRUD from here --------
    private boolean insertar() {

        String url = Constants.URL + "footbocking/addLocal.php";

        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(8); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("nombre", nombreLocal.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("direccion", direccion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("numero_canchas", numeroCanchas.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("precio_hora", precio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("id_usuario", id.trim()));
        nameValuePairs.add(new BasicNameValuePair("lat", lat.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("lon", lon.getText().toString().trim()));


        boolean response = APIHandler.POST(url, nameValuePairs);
        return response;
    }

    public void IrAdministrar(View view) {
        Intent IrLista = new Intent(this, AdministrarCanchas.class);
        IrLista.putExtra("ID",id);
        IrLista.putExtra("Id_canchas",id_canchas);
        startActivity(IrLista);
    }

//----------Eventos del AsyncTask para los botones

    class Insertar extends AsyncTask<String, String, String> {
        private Activity context;

        Insertar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (insertar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Usuario creado", Toast.LENGTH_LONG).show();
                        nombreLocal.setText("");
                        direccion.setText("");
                        numeroCanchas.setText("");
                        precio.setText("");
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Usuario no creado", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng Cali = new LatLng(3.442657, -76.3542673);
        MarkerOptions marc = new MarkerOptions();
        marc.title("Cursor");
        marc.draggable(true);
        marc.position(Cali);
        mMap.addMarker(marc);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Cali));

        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
                lat.setText(marker.getPosition().latitude+"");
                lon.setText(marker.getPosition().longitude+"");
            }
        });

    }
}