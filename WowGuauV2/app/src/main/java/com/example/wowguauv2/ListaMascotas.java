package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListaMascotas extends AppCompatActivity {

    public static final String MASCOTAS_PATH = "mascotas/";

    ListView listVMascotas;
    String[] mProjection;
    AdapterListMascotas mAdapterLM;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    StorageReference mStorageRef;

    ArrayList<Mascota> mascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mascotas =  new ArrayList<Mascota>();
        loadMascotas();

        listVMascotas = findViewById(R.id.listVMascotas);
        mAdapterLM = new AdapterListMascotas(this,null,0);
        listVMascotas.setAdapter(mAdapterLM);

    }

    public void loadMascotas(){
        myRef = database.getReference(MASCOTAS_PATH);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsh:dataSnapshot.getChildren()){
                    Mascota m = dsh.getValue(Mascota.class);
                    mascotas.add(m);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("errorLectura", "error en la consulta", databaseError.toException());
            }
        });
    }
}
