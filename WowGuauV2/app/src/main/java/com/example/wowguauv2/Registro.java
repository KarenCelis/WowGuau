package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {

    Button siguiente;
    EditText txtCorreo, txtContraseña, txtConfirmarContraseña, txtnombre, txtedad, txtdireccion;
    TextView lat, lon;
    Button btn_registrar;
    Button calc;
    ProgressBar progressBar;
    Usuario usuario;


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
                usuario = new Usuario(name, email, age, address, 2.5, 2.5, "asdfg", "dfgh");
                if (password.equals(confPassword)) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), PPrincipalCliente.class));
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
}

