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

        Button boton = (Button) findViewById( R.id.inicioButton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( v.getContext(), Home.class);
                startActivityForResult(intent, 0);
            }
        });

        TextView regis = (TextView)findViewById( R.id.registro);

        regis.setOnClickListener( new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( v.getContext(), Register.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
