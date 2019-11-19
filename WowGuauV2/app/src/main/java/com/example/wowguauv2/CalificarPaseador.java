package com.example.wowguauv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CalificarPaseador extends AppCompatActivity {


    ListView listvPaseos;
    ImageView imgvPaseador,imgvMascotaa;
    TextView txtvNomPaseador, txtvCalificacion, txtvNombreMascota, txtvFechaPaseo;
    EditText edttCalificar;
    Button btnCalificar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_paseador);

        listvPaseos = findViewById(R.id.listvPaseos);
        imgvPaseador = findViewById(R.id.imgvPaseador);
        imgvMascotaa = findViewById(R.id.imgvMascotaa);
        txtvNomPaseador = findViewById(R.id.txtvNomPaseador);
        txtvCalificacion = findViewById(R.id.txtCalificacion);
        txtvNombreMascota = findViewById(R.id.txtvNombreMas);
        txtvFechaPaseo = findViewById(R.id.txtvFechaPaseo);
        edttCalificar = findViewById(R.id.edttCalificacion);
        btnCalificar = findViewById(R.id.btnCalificar);


    }


}
