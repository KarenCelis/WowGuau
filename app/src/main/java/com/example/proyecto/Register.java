package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.view.View.VISIBLE;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );
        final CheckBox check = (CheckBox) findViewById( R.id.checkBox);
        final ImageButton boton = (ImageButton) findViewById ( R.id.sigButton );
        boton.setVisibility ( View.INVISIBLE );
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check.isChecked ()){
                    boton.setVisibility ( VISIBLE );
                }
            }
        });
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( v.getContext(), TipoUsuario.class);
                startActivityForResult(intent, 0);
            }
        });
        TextView terms = (TextView)findViewById( R.id.termycond);

        terms.setOnClickListener( new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( v.getContext(), TerminosCondiciones.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
