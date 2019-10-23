package com.example.wowguauv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DetalleySolicitudPaseador extends AppCompatActivity {

    ListView lv2;

    String [][] datos = {
            {"Jorge Paredes", "3.00 km"},
            {"Juan Ortiz", "4.00 km"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalley_solicitud_paseador);

        lv2 = (ListView) findViewById(R.id.lv2);
        lv2.setAdapter(new Adaptador(this, datos));

        
    }
}
