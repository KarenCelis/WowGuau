package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "AUTH:";

    private FirebaseAuth mAuth;
    private EditText mUser;
    private EditText mPassword;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = (EditText) findViewById(R.id.emailmain);
        mPassword = (EditText) findViewById(R.id.password);
        login = (Button) findViewById ( R.id.btnInicioSesion );
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                    signInUser(mUser.getText().toString(), mPassword.getText().toString());
                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
        TextView textViewinicioRegistro = (TextView)findViewById( R.id.textViewinicioRegistro);

        textViewinicioRegistro.setOnClickListener( new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( v.getContext(), Register.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            Intent intent = new Intent(getBaseContext(),HomeDueno.class);
            intent.putExtra("user",currentUser.getEmail());
            startActivity(intent);
        }else{
            mUser.setText("");
            mPassword.setText("");
        }
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = mUser.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mUser.setError("Required.");
            valid = false;
        } else {
            mUser.setError(null);
        }
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }
        return valid;
    }

    private void signInUser(String email, String password) {
        if (validateForm()) {
            mAuth.signInWithEmailAndPassword(email, password) .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI
                        Log.d(TAG, "signInWithEmail:success"); FirebaseUser user = mAuth.getCurrentUser(); updateUI(user);
                        Intent intent = new Intent ( getApplicationContext (), HomeDueno.class);
                        startActivityForResult (intent,0 );
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException()); Toast.makeText(MainActivity.this, "Authentication failed.",
                                                                                                   Toast.LENGTH_SHORT).show(); updateUI(null);
                    }
                }
            }); }
    }

}
