package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListaPaseadoresCercanos extends AppCompatActivity {

    public static final String PATH_PASEADOR = "user/paseador/";
    public static final int LOCATION = 7;
    double RADIUS_OF_EARTH_KM = 6371;
    private FusedLocationProviderClient mFusedLocation;
    public double miLatitud;
    public double miLonguitud;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(PATH_PASEADOR);
    ArrayList<String> distPaseador = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_lista_paseadores_cercanos);
        ListView list_paseador = findViewById(R.id.listpaseador);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, distPaseador);
        list_paseador.setAdapter(adapter);


        askPermission();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permiso de localización denegado!", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(this, "Permiso de localización aceptado!", Toast.LENGTH_LONG).show();
            mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    Log.i("LOCATION","OnSuccess");

                    if(location != null) {

                        miLatitud = location.getLatitude();
                        miLonguitud = location.getLongitude();
                        loadUsers(myRef,distPaseador, miLatitud, miLonguitud);
                    }
                }
            });
        }
    }


    public void loadUsers(DatabaseReference myRef, final ArrayList distPaseador, final double miLatitud, final double miLonguitud) {

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Usuario paseador = dataSnapshot.getValue(Usuario.class);
                String nombre = paseador.getNombre();
                double lat2 = paseador.getLatitud();
                double long2 = paseador.getLongitud();

                if(distance(lat2,long2, miLatitud,miLonguitud) <= 5){

                    distPaseador.add(nombre);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void askPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronouslyÂ  Â
            //    Toast.makeText(this, "Se necesita el permiso para ver los paseadores!", Toast.LENGTH_LONG).show();
            }
            // Request the permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION);
        }
    }

    public double distance(double lat1, double long1, double lat2, double long2) {

        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result*100.0)/100.0;
    }
}
