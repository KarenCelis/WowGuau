package com.example.wowguauv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PerfilPaseador extends AppCompatActivity {

    TextView txtNombre;
    TextView txtExperiencia;
    TextView txtAniosExp;
    TextView txtDistancia;
    TextView txtCalificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_paseador);

        txtNombre = findViewById(R.id.txtNombre);
        txtExperiencia = findViewById(R.id.txtExperiencia);
        txtAniosExp = findViewById(R.id.txtAniosExp);
        txtDistancia = findViewById(R.id.txtCalificacion);
        txtCalificacion = findViewById(R.id.txtCalificacion);
    }
}
