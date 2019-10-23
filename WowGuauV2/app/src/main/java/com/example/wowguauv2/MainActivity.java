package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button iniciarSesion;
    Button iniciarSesionp;
    Button registrarse;
    private FirebaseAuth firebaseAuth;
    EditText txtCorreo, txtContraseña;
    Button Iniciar;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Usuario myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarSesionp = findViewById(R.id.IniciarSesionP);
        registrarse = findViewById(R.id.Registrar);
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().setTitle("Bienvenido a WowGuau");
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Registro.class));
            }
        });
        ////////////////

        iniciarSesionp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListaSolicitudesPaseador.class));
            }
        });

        txtCorreo = findViewById(R.id.correo);
        txtContraseña = findViewById(R.id.contraseña);
        Iniciar = findViewById(R.id.IniciarSesion);
        firebaseAuth = FirebaseAuth.getInstance();
        Iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtCorreo.getText().toString().trim();
                String password = txtContraseña.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Ingrese su correo", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(MainActivity.this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (password.length() < 6) {

                    Toast.makeText(MainActivity.this, "Contraseña muy corta", Toast.LENGTH_SHORT).show();


                }
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.i("TAG", "onComplete: " + firebaseAuth.getUid());
                                    myRef = database.getReference("user/client");
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Log.i("TAG", "onDataChange: " + dataSnapshot);
                                            myUser = dataSnapshot.child(firebaseAuth.getUid()).getValue(Usuario.class);
                                            if (myUser != null) {
                                                Log.i("TAG", "Encontró usuario: " + myUser.getCorreo());
                                                String name = myUser.getNombre();
                                                int age = myUser.getEdad();
                                                Toast.makeText(getApplicationContext(), name + ":" + age, Toast.LENGTH_SHORT).show();
                                                if (myUser.getTipo().equals("Cliente")) {
                                                    startActivity(new Intent(getApplicationContext(), PPrincipalCliente.class));
                                                } else {
                                                    startActivity(new Intent(getApplicationContext(), Paseador.class));
                                                }
                                            } else {
                                                myRef = database.getReference("user/paseador");
                                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        myUser = dataSnapshot.child(firebaseAuth.getUid()).getValue(Usuario.class);
                                                        if (myUser != null) {
                                                            Log.i("TAG", "Encontró usuario: " + myUser.getCorreo());
                                                            String name = myUser.getNombre();
                                                            int age = myUser.getEdad();
                                                            Toast.makeText(getApplicationContext(), name + ":" + age, Toast.LENGTH_SHORT).show();
                                                            if (myUser.getTipo().equals("Cliente")) {
                                                                startActivity(new Intent(getApplicationContext(), PPrincipalCliente.class));
                                                            } else {
                                                                startActivity(new Intent(getApplicationContext(), ListaSolicitudesPaseador.class));
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        Log.w("TAG", "error en la consulta", databaseError.toException());
                                                    }
                                                });
                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.w("TAG", "error en la consulta", databaseError.toException());
                                        }
                                    });


                                    //if de intent

                                } else {
                                    Toast.makeText(MainActivity.this, "Error al Iniciar Sesión", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            myRef = database.getReference("user/client");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("TAG", "onDataChange: " + dataSnapshot);
                    myUser = dataSnapshot.child(firebaseAuth.getUid()).getValue(Usuario.class);
                    if (myUser != null) {
                        Log.i("TAG", "Encontró usuario: " + myUser.getCorreo());
                        String name = myUser.getNombre();
                        int age = myUser.getEdad();
                        Toast.makeText(getApplicationContext(), name + ":" + age, Toast.LENGTH_SHORT).show();
                        if (myUser.getTipo().equals("Cliente")) {
                            startActivity(new Intent(getApplicationContext(), PPrincipalCliente.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), Paseador.class));
                        }
                    } else {
                        myRef = database.getReference("user/paseador");
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                myUser = dataSnapshot.child(firebaseAuth.getUid()).getValue(Usuario.class);
                                if (myUser != null) {
                                    Log.i("TAG", "Encontró usuario: " + myUser.getCorreo());
                                    String name = myUser.getNombre();
                                    int age = myUser.getEdad();
                                    Toast.makeText(getApplicationContext(), name + ":" + age, Toast.LENGTH_SHORT).show();
                                    if (myUser.getTipo().equals("Cliente")) {
                                        startActivity(new Intent(getApplicationContext(), PPrincipalCliente.class));
                                    } else {
                                        startActivity(new Intent(getApplicationContext(), ListaSolicitudesPaseador.class));
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("TAG", "error en la consulta", databaseError.toException());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("TAG", "error en la consulta", databaseError.toException());
                }
            });
        } else {
            txtCorreo.setText("");
            txtContraseña.setText("");
        }
    }

    public void btn_RegistrarF(View view) {
        startActivity(new Intent(getApplicationContext(), Registro.class));
    }


}
