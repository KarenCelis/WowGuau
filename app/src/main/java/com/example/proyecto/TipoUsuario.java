package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TipoUsuario extends AppCompatActivity {
    ImageButton Boton_Ic;
    ImageButton Boton_Icono_P;
    Button Boton_D;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_usuario);
        Boton_Ic= findViewById(R.id.Boton_Icono_Dueño);
        Boton_Icono_P= findViewById(R.id.Boton_Icono_Paseador);
        Boton_D=findViewById(R.id.Boton_Dueño);

        Boton_Ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegistrarMascota.class);
                startActivity(intent);

            }
        });
        Boton_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegistrarMascota.class);
                startActivity(intent);
            }
        });

    }
}
