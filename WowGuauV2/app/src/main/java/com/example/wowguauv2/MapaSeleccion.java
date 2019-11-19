package com.example.wowguauv2;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapaSeleccion extends FragmentActivity implements OnMapReadyCallback {

    public static final double lowerLeftLatitude= 4.468267;
    public static final double lowerLeftLongitude= -74.179923;
    public static final double upperRightLatitude= 4.828413;
    public static final double upperRigthLongitude= -73.990422;
    int dirSele = 0;

    MapView mMapView;
    View v;
    Button botonseleccion;
    private GoogleMap mMap;
    private Marker lastmarker;
    private TextView latlong;
    Geocoder mGeocoder;
    private String direccion;
    ArrayList<Address> direccionesAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_seleccion);

        latlong = findViewById(R.id.latlong);
        botonseleccion = findViewById(R.id.Seleccionarbutton);
        mGeocoder = new Geocoder(getBaseContext());
        direccion = getIntent().getStringExtra("pos");

        try {
            if(!direccion.isEmpty() || direccion!="") {
                List<Address> direccionesL = mGeocoder.getFromLocationName(direccion,5,lowerLeftLatitude,lowerLeftLongitude,upperRightLatitude,upperRigthLongitude);
                direccionesAL = new ArrayList<>(direccionesL);
            }else {Toast.makeText(this, "Dirección no encontrada", Toast.LENGTH_SHORT).show();}
        } catch (IOException e) {
            e.printStackTrace();
        }


        MapsInitializer.initialize(this);
        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);

        //lastmarker=null;

        botonseleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });
    }

    public void regresar() {
        if (lastmarker != null) {

            double lati = lastmarker.getPosition().latitude;
            double longi = lastmarker.getPosition().longitude;

            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putDouble("lat", lati);
            bundle.putDouble("long", longi);
            intent.putExtras(bundle);
            Log.i("HOLA", "regresar: "+String.valueOf(intent.getExtras().get("lat")));
            setResult(Activity.RESULT_OK,intent);
            finish();
        } else {
            Toast toast = Toast.makeText(this, "No a seleccionado ningún punto aun.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    private void seleccion(LatLng latLng) {
        if (lastmarker != null) {
            lastmarker.remove();
        }
        lastmarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Aqui").icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder322)));
        latlong.setText("Lat: " + latLng.latitude + " Long: " + latLng.longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setMapStyle(MapStyleOptions
                .loadRawResourceStyle(this, R.raw.mapa));


        if(direccionesAL!=null && !direccionesAL.isEmpty()){

            if(dirSele>=0&&dirSele<direccionesAL.size()){
                Address direccion_actual = direccionesAL.get(dirSele);
                LatLng posLL = new LatLng(direccion_actual.getLatitude(), direccion_actual.getLongitude());
                lastmarker = mMap.addMarker(new MarkerOptions().position(posLL).title(direccion_actual.getFeatureName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder322)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posLL, 12));
                latlong.setText("Lat = "+posLL.latitude+" Long = "+posLL.longitude);
            }

        }

       /* LatLng location = new LatLng(4.643967681564348, -74.09724276512863);
        lastmarker = mMap.addMarker(new MarkerOptions().position(location).title("Aqui"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));

        lastmarker.remove();

        lastmarker = null;*/

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                seleccion(latLng);
            }
        });


    }
}
