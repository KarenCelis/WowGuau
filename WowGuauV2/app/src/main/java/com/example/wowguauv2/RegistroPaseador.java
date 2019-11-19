package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.wowguauv2.Registro.PATHUSER;

public class RegistroPaseador extends AppCompatActivity {
    Button sigu;
    public static final String PATHPASEADOR = "user/paseador/";
    EditText txtDescripcion, txtAños, txtCertificados;
    ProgressBar progressBar;
    Paseador paseador;
    private FirebaseAuth firebaseAuth;
    Usuario usuario;
    String password;
    String userid;
    Uri profile;
    StorageReference mStorageRef;
    DatabaseReference myRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paseador);
        sigu = findViewById(R.id.btnregistr);
        txtDescripcion = findViewById(R.id.txt_descripcion);
        txtAños = findViewById(R.id.txt_años);
        txtCertificados = findViewById(R.id.txt_Certificados);
        getSupportActionBar().setTitle("Registro Paseador");
        firebaseAuth = FirebaseAuth.getInstance();
        usuario = (Usuario) getIntent().getSerializableExtra("Editing");
        password = getIntent().getStringExtra("Contraseña");
        profile=Uri.parse((String) getIntent().getSerializableExtra("Profile"));
        sigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = txtDescripcion.getText().toString().trim();
                Integer ages = Integer.valueOf(txtAños.getText().toString().trim());
                String certificate = txtCertificados.getText().toString().trim();


                if (TextUtils.isEmpty(description)) {
                    Toast.makeText(RegistroPaseador.this, "Ingrese una descripción", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ages.toString())) {
                    Toast.makeText(RegistroPaseador.this, "Ingrese sus años de experiencia", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(certificate)) {
                    Toast.makeText(RegistroPaseador.this, "Ingrese una descripción de los certificados", Toast.LENGTH_SHORT).show();
                    return;
                }
                //progressBar.setVisibility(view.VISIBLE);
                paseador = new Paseador(usuario.getNombre(), usuario.getCorreo(), usuario.getEdad(), usuario.getDireccion(), usuario.getLatitud(), usuario.getLongitud(), usuario.getPathFoto(), usuario.getTipo(), description, ages, certificate, false);
                paseador.setEstado(false);
                paseador.setPaseosRealizados(0);
                paseador.setCalificacion(0);
                    //AUTENTICACION
                    firebaseAuth.createUserWithEmailAndPassword(usuario.getCorreo(), password)
                            .addOnCompleteListener(RegistroPaseador.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        userid = task.getResult().getUser().getUid();
                                        //STORAGE
                                        mStorageRef = FirebaseStorage.getInstance().getReference();
                                        StorageReference userRef = mStorageRef.child("users/" + userid + "/profile");

                                        userRef.putFile(profile)
                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        Log.i("TAG", "Exito subida de imagen");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception exception) {
                                                        Log.e("TAG", "Fallo subida de imagen");
                                                    }
                                                });
                                        //DATAUSER
                                        paseador.setPathFoto("users/" + userid + "/profile");
                                        myRef = database.getReference(PATHPASEADOR + userid);
                                        myRef.setValue(paseador);

                                        firebaseAuth.signOut();

                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        Toast.makeText(RegistroPaseador.this, "Registrad@", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegistroPaseador.this, "Error en la autenticacion", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });

                // startActivity(new Intent(getApplicationContext(),ListaSolicitudesPaseador.class));
            }
        });


    }
}
