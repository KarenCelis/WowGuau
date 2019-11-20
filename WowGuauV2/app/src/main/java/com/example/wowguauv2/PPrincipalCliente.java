package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PPrincipalCliente extends AppCompatActivity {
Button registrarM;
Button listaM;
Button listaP;
Button listaPUbicacion;
Button paseoCurso;
Boolean existenPaseos=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pprincipal_cliente);
        registrarM=findViewById(R.id.RegistrarMascota);
        listaM=findViewById(R.id.ListaMascotas);
        listaP=findViewById(R.id.ListaPaseadores);
        paseoCurso=findViewById(R.id.CerrarSesion);
        listaPUbicacion = findViewById(R.id.btnPaseadorUbicacion);
        if(!existenPaseos){

            paseoCurso.setEnabled(false);

        }
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

        paseoCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PaseoEnCursoC.class));
            }
        });

        listaPUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListaPaseadoresUbicacion.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.signOutMenuItd){

            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
