package com.example.wowguauv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaseoEnCursoC extends AppCompatActivity {
Button VerPaseoCurso;
Button SolicitarImg;
Button CalificarPaseador;
Button PagarPaseo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paseo_en_curso_c);

        VerPaseoCurso=findViewById(R.id.btnVerMapaPaseo);
        SolicitarImg=findViewById(R.id.btnSolicitarImg);
        CalificarPaseador=findViewById(R.id.btnCalificarPase);
        PagarPaseo=findViewById(R.id.btnPagarPaseo);

        VerPaseoCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),VerMapaPaseoEnCurso.class));
            }
        });

        CalificarPaseador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CalificarPaseador.class));
            }
        });

        PagarPaseo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
