package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CertificadoPaseador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_certificado_paseador );

        final ImageButton boton = (ImageButton) findViewById ( R.id.Boton_Siguiente_Cert_Paseador );

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( v.getContext(), HomePaseador.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
