package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListaPaseadoresCercanos extends AppCompatActivity {

    public static final String PATH_PASEADOR = "user/paseador/";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(PATH_PASEADOR);
    ArrayList<String> distPaseador = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_paseadores_cercanos);

        loadUsers(myRef);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, distPaseador);
        ListView list_paseador = findViewById(R.id.listpaseador);
        list_paseador.setAdapter(adapter);
    }


    public void loadUsers(DatabaseReference myRef) {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Usuario paseador = singleSnapshot.getValue(Usuario.class);
                    String nombrePas = paseador.getNombre();

                    for(int i = 0; i < distPaseador.size(); i++){

                        distPaseador.add("Hola");
                    }

                    Toast.makeText(ListaPaseadoresCercanos.this, nombrePas, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ListaPaseadoresCercanos.this,"ERROOOOR", Toast.LENGTH_LONG).show();
            }
        });

    }
}
