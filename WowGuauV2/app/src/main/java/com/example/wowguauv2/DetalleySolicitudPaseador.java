package com.example.wowguauv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleySolicitudPaseador extends AppCompatActivity {

    public static final String PATH_USERS="user/client/";

    FirebaseDatabase database;
    DatabaseReference myRef;

    ListView lv2;

    String [][] datos = {
            {"Croky", "Pug"},
            {"Benny", "Pastor"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalley_solicitud_paseador);

        database= FirebaseDatabase.getInstance();

        lv2 = (ListView) findViewById(R.id.lv2);
        lv2.setAdapter(new AdaptadorLM(this, datos));

        
    }
}
