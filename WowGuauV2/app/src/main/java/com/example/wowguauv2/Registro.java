package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registro extends AppCompatActivity {

    public static final int READ_EXTERNAL_STORAGE2 = 0;
    public static final int IMAGE_PICKER_REQUEST2 = 2;
    public static final int REQUEST_IMAGE_CAPTURE = 3;

    public static final String PATHUSER = "user/client/";
    Button siguiente;
    EditText txtCorreo, txtContraseña, txtConfirmarContraseña, txtnombre, txtedad, txtdireccion;
    TextView lat, lon;
    Button btn_registrar;
    Button calc;
    ProgressBar progressBar;
    Usuario usuario;
    Spinner spine;
    //FireBase Realdatabase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    //imagen
    ImageView imagen;
    ImageButton gallerybutton;
    ImageButton camerabutton;
    Uri profile;
    String userid;
    BitmapFactory.Options options;
    String mCurrentPhotoPath;
    //Storage
    StorageReference mStorageRef;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

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
        imagen = findViewById(R.id.imagePrev);
        gallerybutton = findViewById(R.id.gallerybutton);
        camerabutton = findViewById(R.id.cameraButton);
        spine = findViewById(R.id.spinner);


        //GALERIA
        gallerybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();
            }
        });
        //CAMARA
        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        //MAPA
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

                if(spine.getSelectedItem().toString().equals("Cliente"))
                {
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
                    usuario = new Usuario(name, email, age, address, Double.valueOf(lat.getText().toString()), Double.valueOf((String) lon.getText()), "Nofoto", spine.getSelectedItem().toString());
                    if (password.equals(confPassword)) {
                        //AUTENTICACION
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
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
                                            usuario.setPathFoto("users/" + userid + "/profile");
                                            myRef = database.getReference(PATHUSER + userid);
                                            myRef.setValue(usuario);

                                            firebaseAuth.signOut();

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
                else{
                    startActivity(new Intent(getApplicationContext(), RegistroPaseador.class));
                }


            }
        });
    }

    //////////////////////PERMISOS
    private void requestPermission(Activity context, String permission, String explanation, int requestId) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                Toast.makeText(context, explanation, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, requestId);
        }
    }

    //////////////GALLERY
    private void addImage() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Intent pickImage = new Intent(Intent.ACTION_PICK);
            pickImage.setType("image/*");
            startActivityForResult(pickImage, IMAGE_PICKER_REQUEST2);
        } else {
            Toast.makeText(this, "Sin acceso a almacenamietno", Toast.LENGTH_LONG).show();
            requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE,
                    "Se necesita acceder al almacenamiento", READ_EXTERNAL_STORAGE2);
        }


    }

    ///////////////CAMERA

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePicture() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Log.i("TAG", "takePicture: " + photoFile);
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.wowguauv2",
                            photoFile);
                    profile = photoURI;
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                }
            }
        } else {
            Toast.makeText(this,
                    "Sin acceso a camara",
                    Toast.LENGTH_LONG).show();
            requestPermission(this, Manifest.permission.CAMERA,
                    "Se necesita acceder a la camara", REQUEST_IMAGE_CAPTURE);
        }
    }


    /////////////MAPA SELECCION
    //Metodo que se acciona cuando seleccion una ubicacion y regresa
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle b = data.getExtras();
                    Log.i("LOG", String.valueOf(b.get("lat")));
                    lat.setText(String.valueOf(b.get("lat")));
                    lon.setText(String.valueOf(b.get("long")));
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                }
                return;
            }
            case IMAGE_PICKER_REQUEST2: {
                if (resultCode == RESULT_OK) {
                    try {
                        profile = data.getData();
                        Log.i("holas", "" + profile);

                        final InputStream imageStream = getContentResolver().openInputStream(profile);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imagen.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case REQUEST_IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {


                    File imgFile = new File(mCurrentPhotoPath);


                    if (imgFile.exists()) {
                        Log.i("TAG", "-->" + imgFile.getPath());
                        options = new BitmapFactory.Options();
                        options.inSampleSize = 16;
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getPath(), options);
                        imagen.setImageBitmap(myBitmap);
                    }

                }

                return;
            }

        }


    }//onActivityResult
}

