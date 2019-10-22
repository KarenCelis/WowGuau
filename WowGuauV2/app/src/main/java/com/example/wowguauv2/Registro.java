package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    public static final String PATHUSER="user/client/";
    Button siguiente;
    EditText txtCorreo, txtContraseña, txtConfirmarContraseña, txtnombre, txtedad, txtdireccion;
    TextView lat, lon;
    Button btn_registrar;
    Button calc;
    ProgressBar progressBar;
    Usuario usuario;
    //FireBase Realdatabase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;



    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        siguiente=findViewById(R.id.btnregistr);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistroPaseador.class));
            }
        });
        getSupportActionBar().setTitle("Formulario de Registro");
        txtCorreo = findViewById(R.id.txt_correo);
        txtContraseña = findViewById(R.id.txt_contrasena);
        txtConfirmarContraseña = findViewById(R.id.txt_confirmcontr);
        btn_registrar = findViewById(R.id.btnregistr);
        progressBar = findViewById(R.id.progressBar1);
        txtnombre = findViewById(R.id.txt_nombre);
        txtedad = findViewById(R.id.txt_edad);
        txtdireccion = findViewById(R.id.txt_direcccion);
        lat = findViewById(R.id.Latitud);
        lon = findViewById(R.id.Longitud);
        calc = findViewById(R.id.Ir);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MapaSeleccion.class);
                startActivityForResult(i, 1);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        btn_registrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = txtCorreo.getText().toString().trim();
                String password = txtContraseña.getText().toString().trim();
                String confPassword = txtConfirmarContraseña.getText().toString().trim();
                String name = txtnombre.getText().toString().trim();
                Integer age = Integer.valueOf(txtedad.getText().toString().trim());
                String address = txtdireccion.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(Registro.this, "Ingrese su correo", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(Registro.this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(confPassword)) {

                    Toast.makeText(Registro.this, "Confirme su contraseña", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (password.length() < 6) {

                    Toast.makeText(Registro.this, "Contraseña muy corta", Toast.LENGTH_SHORT).show();


                }
                progressBar.setVisibility(view.VISIBLE);
                usuario = new Usuario(name, email, age, address, Double.valueOf(lat.getText().toString()), Double.valueOf((String) lon.getText()), "asdfg", "dfgh");
                if (password.equals(confPassword)) {
                    //AUTENTICACION
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        //DATAUSER
                                        myRef = database.getReference(PATHUSER+task.getResult().getUser().getUid());
                                        myRef.setValue(usuario);

                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        Toast.makeText(Registro.this, "Registrad@", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Registro.this, "Error en la autenticacion", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });



                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                Log.i("LOG", String.valueOf(b.get("lat")));
                lat.setText(String.valueOf(b.get("lat")));
                lon.setText(String.valueOf(b.get("long")));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}

