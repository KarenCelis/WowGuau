package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VerMapaPaseoEnCurso extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private Marker oldmark;
    private Marker newmark;
    private Marker lastmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mapa_paseo_en_curso);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        database= FirebaseDatabase.getInstance();
        myRef = database.getReference("/user/paseador/obnrSf46A7RjMq7p2wmODj8cPo23/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Log.i("koko", "Encontró usuario: " + dataSnapshot.toString());
                    Paseador myUser = dataSnapshot.getValue(Paseador.class);
                    Log.i("koko", "Encontró usuario: " + myUser.toString());
                    String name = myUser.getNombre();
                    Double lati = myUser.getLatitud();
                    Double longi = myUser.getLongitud();
                    String msj = ""+ lati + "," + longi;
                    Toast.makeText(VerMapaPaseoEnCurso.this, name + ":" + msj, Toast.LENGTH_SHORT).show();

                    LatLng walker = new LatLng(lati, longi);
                    oldmark.remove();
                    newmark = mMap.addMarker(new MarkerOptions().position(walker).title("Paseador").icon(BitmapDescriptorFactory.fromResource(R.drawable.placewalker2)));
                    oldmark = newmark;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(walker,19));
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("koko", "error en la consulta", databaseError.toException());
            }
        });
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
        LatLng bog = new LatLng(4, -74);
        oldmark = mMap.addMarker(new MarkerOptions().position(bog).title("Bog"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bog,19));
    }
}
