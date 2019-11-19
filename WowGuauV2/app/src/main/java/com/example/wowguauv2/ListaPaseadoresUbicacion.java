package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListaPaseadoresUbicacion extends AppCompatActivity {

    EditText txtDireccion;
    Button btnBuscarDir;
    Double latDireccion;
    Double longDireccion;
    ListView ListaPaseadores;
    final static int MAP_LOCATION = 1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final String PATH_PASEADOR = "user/paseador/";
    DatabaseReference myRef = database.getReference(PATH_PASEADOR);
    double RADIUS_OF_EARTH_KM = 6371;
    ArrayList<Paseador> paseadorUb = new ArrayList<>();
    ArrayList<String> nombPaseador = new ArrayList<>();
    ArrayAdapter<String> adapterNomb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_paseadores_ubicacion);

        txtDireccion = findViewById(R.id.txtDireccion);
        btnBuscarDir = findViewById(R.id.btnBuscarDir);
        ListaPaseadores = findViewById(R.id.ListaPaseadores);

        btnBuscarDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMap = new Intent(view.getContext(), MapaSeleccion.class);
                if(txtDireccion.getText().toString() != null){

                    intentMap.putExtra("pos", txtDireccion.getText().toString());
                    startActivityForResult(intentMap, MAP_LOCATION);
                }
            }
        });

        ListaPaseadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intDatos = new Intent(getBaseContext(),PerfilPaseador.class);
                startActivity(intDatos);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 1:{

                if(resultCode == Activity.RESULT_OK){

                    Bundle b = data.getExtras();
                    latDireccion = b.getDouble("lat");
                    longDireccion = b.getDouble("long");
                    loadPaseadores();
                }
            }
        }
    }

    public void loadPaseadores(){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){

                    Paseador paseador = singleSnapshot.getValue(Paseador.class);
                    Double latPasead = paseador.getLatitud();
                    Double longPasead = paseador.getLongitud();

                    if(distance(latPasead,longPasead, latDireccion, longDireccion) <= 5.0){

                        paseadorUb.add(paseador);
                        nombPaseador.add(paseador.getNombre());
                    }
                }

                adapterNomb = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, nombPaseador);
                ListaPaseadores.setAdapter(adapterNomb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.w("Error en consulta", databaseError.toException());

            }
        });
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
