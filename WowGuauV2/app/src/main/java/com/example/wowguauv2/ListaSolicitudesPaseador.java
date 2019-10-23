package com.example.wowguauv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;

public class ListaSolicitudesPaseador extends AppCompatActivity {

    Switch switchD;
    ListView lv1;

    String [][] datos = {
            {"Jorge Paredes", "3.00 km"},
            {"Juan Ortiz", "4.00 km"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_solicitudes_paseador);

        switchD = (Switch) findViewById(R.id.switch_disponible);

        switchD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                if (switchD.isChecked()){
                    switchD.setText(getResources().getText(R.string.disponible));
                    switchD.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                }
                else {
                    switchD.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.rojo));
                }
            }
        });

        lv1 = (ListView) findViewById(R.id.lv1);
        lv1.setAdapter(new Adaptador(this, datos));

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), DetalleySolicitudPaseador.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemClicked = item.getItemId();
        if(itemClicked == R.id.menuLogOut){
            //mAuth.signOut();
            Intent intent = new Intent(ListaSolicitudesPaseador.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
