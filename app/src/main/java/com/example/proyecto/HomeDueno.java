package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeDueno extends  Activity {

    Button btnBuscarPaseo,btnProgramarPaseo,btnHistorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dueno);

        btnBuscarPaseo = findViewById(R.id.btnBuscarPaseo);
        btnProgramarPaseo = findViewById(R.id.btnProgramarPaseo);
        btnHistorial = findViewById(R.id.btnHistorial);

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),HistorialDueno.class);
                startActivity(intent);
            }
        });

        btnProgramarPaseo = findViewById(R.id.btnProgramarPaseo);
        btnProgramarPaseo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(view.getContext(),BuscarPaseo.class);
                startActivity(intent);
            }
        });
    }
}
