package com.example.wowguauv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PPrincipalCliente extends AppCompatActivity {
Button registrarM;
Button listaM;
Button listaP;
Button listaPUbicacion;
Button cerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pprincipal_cliente);
        registrarM=findViewById(R.id.RegistrarMascota);
        listaM=findViewById(R.id.ListaMascotas);
        listaP=findViewById(R.id.ListaPaseadores);
        cerrar=findViewById(R.id.CerrarSesion);
        listaPUbicacion = findViewById(R.id.btnPaseadorUbicacion);

        registrarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrarMascota.class));
            }
        });

        listaM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ListaMascotas.class));
            }
        });

        listaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ListaPaseadoresCercanos.class));
            }
        });
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        listaPUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListaPaseadoresUbicacion.class));
            }
        });
    }

}
