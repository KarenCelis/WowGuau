package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListaMascotas extends AppCompatActivity {

    public static final String MASCOTAS_PATH = "mascotas/";

    ListView listViewMascotas;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    StorageReference mStorageRef;

    ArrayList<Mascota> mascotas;
    List<String> mascotasNombres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        listViewMascotas = findViewById(R.id.listViewMascotas);

        mascotas =  new ArrayList<Mascota>();
        mascotasNombres =  new ArrayList<String>();
        loadMascotas();

    }

    public void loadMascotas(){
        Query q = database.getReference(MASCOTAS_PATH).orderByChild("duenoUid").equalTo(user.getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsh:dataSnapshot.getChildren()){
                    Mascota m = dsh.getValue(Mascota.class);
                    Log.i("consulta", "onDataChange: "+m.getNombre());
                    mascotas.add(m);
                    mascotasNombres.add(m.getNombre());
                    String cambio = "esto es un cambio";
                }
                ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,mascotasNombres);
                listViewMascotas.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("errorLectura", "error en la consulta", databaseError.toException());
            }
        });
    }
}
