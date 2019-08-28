package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnInicioSesion = (Button) findViewById( R.id.btnInicioSesion);
        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( v.getContext(), HomePaseador.class);
                startActivityForResult(intent, 0);
            }
        });

        TextView textViewinicioRegistro = (TextView)findViewById( R.id.textViewinicioRegistro);

        textViewinicioRegistro.setOnClickListener( new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( v.getContext(), Register.class);
                startActivityForResult(intent, 0);
            }
        });


    }
}
