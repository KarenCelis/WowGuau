package com.example.wowguauv2;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class ListaPaseadoresCercanos extends AppCompatActivity {

    public static final String PATH_PASEADOR = "user/paseador/";
    public static final int LOCATION = 7;
    double RADIUS_OF_EARTH_KM = 6371;
    public static final int REQUEST_CHECK_SETTINGS = 9;
    public static final int RESULT_OK = 9;
    private FusedLocationProviderClient mFusedLocation;
    public double miLatitud;
    public double miLonguitud;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(PATH_PASEADOR);
    ArrayList<String> distPaseador = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("wqe", "1");
        setContentView(R.layout.activity_lista_paseadores_cercanos);
        ListView list_paseador = findViewById(R.id.listpaseador);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, distPaseador);
        list_paseador.setAdapter(adapter);
        mLocationRequest = createLocationRequest();

        askPermission();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permiso de localización denegado!", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(this, "Permiso de localización aceptado!", Toast.LENGTH_LONG).show();

            mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addAllLocationRequests(Collections.singleton(mLocationRequest));
            SettingsClient client = LocationServices.getSettingsClient(this);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                    startLocationUpdates();
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    int statusCode = ((ApiException)e).getStatusCode();
                    switch (statusCode){
                        case CommonStatusCodes.RESOLUTION_REQUIRED:
                            try{

                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(ListaPaseadoresCercanos.this, REQUEST_CHECK_SETTINGS);

                            }catch (IntentSender.SendIntentException sendEx){

                            }break;
                        case LocationSettingsStatusCodes
                                .SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });

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

    protected LocationRequest createLocationRequest() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return mLocationRequest;
    }

    public void loadUsers(DatabaseReference myRef, final ArrayList distPaseador, final double miLatitud, final double miLonguitud) {

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Usuario paseador = dataSnapshot.getValue(Usuario.class);
                String nombre = paseador.getNombre();
                double lat2 = paseador.getLatitud();
                double long2 = paseador.getLongitud();

                if(distance(lat2,long2, miLatitud,miLonguitud) <= 5.0){

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

    private void startLocationUpdates(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            mFusedLocation.requestLocationUpdates(mLocationRequest,mLocationCallback,null);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        switch (requestCode){

            case REQUEST_CHECK_SETTINGS:{

                if(resultCode == RESULT_OK){
                    startLocationUpdates();
                }else{
                    Toast.makeText(this,"Sin acceso a localización, harware deshabilitado", Toast.LENGTH_LONG).show();

                }
                return;
            }
        }
    }
}
