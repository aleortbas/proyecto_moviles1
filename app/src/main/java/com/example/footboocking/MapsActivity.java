package com.example.footboocking;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.footboocking.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    MarkerOptions marker;
    Vector<MarkerOptions> markerOptions;

    LatLng alorsetar;

    private  String URL ="http://192.168.0.18:50/api/footbocking/coordenadas.php";
    RequestQueue requestQueue;
    Gson gson;
    Maklumat[] maklumats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gson = new GsonBuilder().create();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markerOptions = new Vector<>();

        alorsetar = new LatLng(3.442657,-76.3542673);
        marker = new MarkerOptions().position(alorsetar).title("Alor Setar").snippet("Cawangan di buka 7am-9pm");

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        //mMap.addMarker(marker);

        for (MarkerOptions mark : markerOptions){
            mMap.addMarker(mark);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alorsetar,8));
        enableMyLocation();
        sendRequest();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String markeTitle = marker.getTitle();

                Intent i = new Intent(MapsActivity.this, infoLocal.class);
                i.putExtra("prueba",markeTitle);
                startActivity(i);

                return false;
            }
        });

    }

    private void enableMyLocation() {

        String perms[] = {"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_NETWORK_STATE"};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                Log.d("hafizxx","permission granted");
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission

            Log.d("hafizxx","permission denied");
            ActivityCompat.requestPermissions(this,perms ,200);

        }
    }

    public void sendRequest() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL,onSucces,onError);
        requestQueue.add(stringRequest);


    }

    public Response.Listener<String> onSucces = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            maklumats = gson.fromJson(response, Maklumat[].class);

            Log.d("Maklumat", "Number of Maklumat Data point : " + maklumats.length);

            if(maklumats.length < 1){
                Toast.makeText(getApplicationContext(), "Problema con JSON", Toast.LENGTH_LONG).show();
                return;
            }

            for(Maklumat info: maklumats){
                Double lat = Double.parseDouble(info.lat);
                Double lon = Double.parseDouble(info.lon);
                String title = info.nombre;
                String snippet = info.direccion;

                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat,lon))
                        .title(title)
                        .snippet(snippet);

                mMap.addMarker(marker);

            }
        }
    };

    public Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
        }
    };
}