package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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

public class CalificarPaseador extends AppCompatActivity {

    public static final String PATH_PASEOS = "paseos/";
    public static final String PATH_PASEADOR = "user/paseador/";
    public static final String PATH_MASCOTAS = "mascotas/";

    ListView listvPaseos;
    ImageView imgvPaseador,imgvMascotaa;
    TextView txtvNomPaseador, txtvCalificacion, txtvNombreMascota, txtvFechaPaseo;
    EditText edttCalificar;
    Button btnCalificar;
    LinearLayout linear1,linear2,linear0;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    StorageReference mStorageRef;

    ArrayList<Paseo> paseos;
    ArrayList<Paseador> paseadores;
    ArrayList<String> paseadoresS;
    Paseador paseador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_paseador);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        listvPaseos = findViewById(R.id.listvPaseos);
        imgvPaseador = findViewById(R.id.imgvPaseador);
        imgvMascotaa = findViewById(R.id.imgvMascotaa);
        txtvNomPaseador = findViewById(R.id.txtvNomPaseador);
        txtvCalificacion = findViewById(R.id.txtvCalificacion);
        txtvNombreMascota = findViewById(R.id.txtvNombreMas);
        txtvFechaPaseo = findViewById(R.id.txtvFechaPaseo);
        edttCalificar = findViewById(R.id.edttCalificacion);
        btnCalificar = findViewById(R.id.btnCalificar);
        linear1 = findViewById(R.id.Linear1);
        linear2 = findViewById(R.id.Linear2);
        linear0 = findViewById(R.id.Linear0);


        paseos = new ArrayList<Paseo>();
        paseadores = new ArrayList<Paseador>();
        paseadoresS = new ArrayList<String>();

        loadPaseos();
        mostrarDetalle();
        //ocultarDetalle();

        listvPaseos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("calificar", "onItemClick: "+listvPaseos.getItemAtPosition(i).toString());
                cargarDetalle(listvPaseos.getItemAtPosition(i).toString());
                mostrarDetalle();
            }
        });

        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edttCalificar.getText().toString().isEmpty() && paseador!=null){

                    float calificacion = paseador.getCalificacion() + (Integer.parseInt(edttCalificar.getText().toString())/(paseador.getPaseosRealizados()+1));
                    database.getReference(PATH_PASEADOR).child(paseador.getUid()).child("calificacion").setValue(calificacion).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Calificación realizada",Toast.LENGTH_LONG).show();
                            paseadoresS.remove(paseador.getNombre());
                            ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,paseadoresS);
                            listvPaseos.setAdapter(adapter);
                            ocultarDetalle();
                        }
                    });
                }
            }
        });

    }

    private void ocultarDetalle(){
        linear0.setVisibility(View.INVISIBLE);
        linear1.setVisibility(View.INVISIBLE);
        linear2.setVisibility(View.INVISIBLE);
    }

    private void mostrarDetalle(){
        linear0.setVisibility(View.VISIBLE);
        linear1.setVisibility(View.VISIBLE);
        linear2.setVisibility(View.VISIBLE);
    }

    private void cargarDetalle(String nombrePas) {

        for(Paseador p : paseadores){
            if(p.getNombre().equals(nombrePas)){
                paseador = p;
                txtvNomPaseador.setText(p.getNombre());
                txtvCalificacion.setText(String.valueOf(p.getCalificacion())+" ★");
                mStorageRef = mStorageRef.child(p.getPathFoto());
                mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                           Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                           imgvPaseador.setImageBitmap(bitmap);
                    }
                });


                for(Paseo pa : paseos){
                    if(pa.getPaseadorUid().equals(p.getUid())){
                        txtvNombreMascota.setText(pa.getNombreMascota());
                        txtvFechaPaseo.setText(pa.getInicio().toString());

                        Query query = database.getReference(PATH_MASCOTAS).orderByChild("nombre").equalTo(pa.getNombreMascota());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dsh : dataSnapshot.getChildren()){
                                    Mascota m = dsh.getValue(Mascota.class);
                                    if(m.getDuenoUid().equals(user.getUid())){
                                        mStorageRef = FirebaseStorage.getInstance().getReference().child(m.getPathFoto());
                                        mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                                imgvMascotaa.setImageBitmap(bitmap);
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }
        }

    }

    public void loadPaseos(){
        Query q = database.getReference(PATH_PASEOS).orderByChild("clienteUid").equalTo(user.getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsh:dataSnapshot.getChildren()){
                    Paseo p = dsh.getValue(Paseo.class);
                    if(!p.isCalificado()&&!p.isActivo()&&p.isAceptado()){
                        paseos.add(p);
                        Query q2 = database.getReference(PATH_PASEADOR).orderByChild("uid").equalTo(p.getPaseadorUid());
                        q2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dsh2 : dataSnapshot.getChildren()){
                                    Paseador pas = dsh2.getValue(Paseador.class);
                                    paseadores.add(pas);
                                    paseadoresS.add(pas.getNombre());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,paseadoresS);
                listvPaseos.setAdapter(adapter);
                listvPaseos.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("errorLectura", "error en la consulta", databaseError.toException());
            }
        });
    }


}
