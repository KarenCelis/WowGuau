package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class ListaSolicitudesPaseador extends AppCompatActivity {

    public static final String PATH_PASEADORES = "user/paseador/";

    Switch switchD;
    ListView lv1;
    TextView textoSolicitudes;
    Button logout;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;

    Context contexto = this;

    String [][] datos = {
            {"Jorge Paredes", "3.00 km"},
            {"Juan Ortiz", "4.00 km"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_solicitudes_paseador);

        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();

        user = mAuth.getCurrentUser();

        myRef = database.getReference(PATH_PASEADORES + user.getUid());
        myRef.child("estado").setValue(true);

        switchD = (Switch) findViewById(R.id.switch_disponible);

        textoSolicitudes = (TextView) findViewById(R.id.textoSolicitudes);

        lv1 = (ListView) findViewById(R.id.lv1);
        lv1.setAdapter(new Adaptador(this, datos));

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), DetalleySolicitudPaseador.class);
                startActivity(intent);
            }
        });

        switchD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "MyClass.getView() — get item number " + user.getUid());
                myRef = database.getReference(PATH_PASEADORES + user.getUid());
                if (switchD.isChecked()){
                    myRef.child("estado").setValue(true);
                    mostrarSolicitudes();

                    switchD.setText(getResources().getText(R.string.disponible));

                    switchD.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                }
                else {
                    myRef.child("estado").setValue(false);
                    quitarSolicitudes();

                    switchD.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.rojo));
                }
            }
        });


    }

    private void quitarSolicitudes() {
        textoSolicitudes.setText(R.string.peticion_estado);
        lv1.setAdapter(null);
    }

    private void mostrarSolicitudes() {
        textoSolicitudes.setText(R.string.seleccione);
        lv1.setAdapter(new Adaptador(contexto, datos));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.signOutMenuItd){

            myRef = database.getReference(PATH_PASEADORES + user.getUid());
            myRef.child("estado").setValue(false);
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
