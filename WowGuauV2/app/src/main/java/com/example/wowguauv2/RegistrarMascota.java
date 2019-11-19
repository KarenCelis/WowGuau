package com.example.wowguauv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrarMascota extends AppCompatActivity {

    Button btnRegistar;
    EditText edtxtNombre,edttxtEdad,edttxtRecomendaciones;
    ImageView imgVMascota;
    Spinner spinnerRaza,spinnerTam;
    ImageButton btnCamara,btnGaleria;

    String mCurrentPhotoPath;
    Uri profile;
    BitmapFactory.Options options;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    StorageReference mStorageRef;


    public static final int REQUEST_IMAGE_CAPTURE_RMASCOTAS = 10;
    public static final int REQUEST_IMAGE_PICKER_RMASCOTAS = 11;
    public static final String MASCOTAS_PATH = "mascotas/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        edtxtNombre = findViewById(R.id.edttNombre);
        edttxtEdad = findViewById(R.id.edttxtEdad);
        edttxtRecomendaciones = findViewById(R.id.edttxtRecomendaciones);
        imgVMascota = findViewById(R.id.imgVMascota);
        spinnerRaza = findViewById(R.id.spinnerRaza);
        spinnerTam = findViewById(R.id.spinnerTamano);
        btnCamara = findViewById(R.id.btnCamara);
        btnGaleria = findViewById(R.id.btnGaleria);
        btnRegistar = findViewById(R.id.btnRegistrar);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();
            }
        });

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarCampos()){
                    myRef = database.getReference(MASCOTAS_PATH);
                    String key = myRef.push().getKey();
                    Mascota mascota = obtenerMascota();
                    mStorageRef.child(MASCOTAS_PATH + key + "/img").putFile(profile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.i("i", "onSuccess: Fotocargada");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("i","onFalure: CargarFoto");
                        }
                    });

                    mascota.setPathFoto(MASCOTAS_PATH + key + "/img");
                    myRef.setValue(mascota).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Mascota agregada correctamente",Toast.LENGTH_LONG).show();
                        }
                    });
                    startActivity(new Intent(getApplicationContext(),ListaMascotas.class));
                }
            }
        });
    }

    public Mascota obtenerMascota(){
        String nombre, raza, recomendaciones, duenoUid,pathFoto;
        int edad;
        Mascota.Size tam=null;

        nombre = edtxtNombre.getText().toString();
        recomendaciones = edttxtRecomendaciones.getText().toString();
        raza = spinnerRaza.getSelectedItem().toString();
        switch (spinnerTam.getSelectedItemPosition()){
            case 1: tam = Mascota.Size.XSmall;
                break;
            case 2: tam = Mascota.Size.Small;
                break;
            case 3: tam = Mascota.Size.Medium;
                break;
            case 4: tam = Mascota.Size.Large;
                break;
            case 5: tam = Mascota.Size.XLarge;
        }
        edad = Integer.parseInt(edttxtEdad.getText().toString());
        duenoUid = user.getUid();
        return  new Mascota(nombre,raza,edad,tam,duenoUid,"",recomendaciones);

    }

    public boolean verificarCampos(){
        boolean retorno = true;
        if(edtxtNombre.getText().equals("")){
            edtxtNombre.setError("Ingrese el nombre de su mascota");
            retorno = false;
        }
        if(edttxtEdad.getText().equals("")){
            edttxtEdad.setError("Ingrese la edad de su mascota");
            retorno = false;
        }
        if(spinnerTam.getSelectedItemPosition()==0){
            Toast.makeText(this,"Seleccione el tamaño de su mascota",Toast.LENGTH_LONG).show();
            retorno = false;
        }
        if(spinnerRaza.getSelectedItemPosition()==0){
            Toast.makeText(this,"Seleccione la raza de su mascota",Toast.LENGTH_LONG).show();
            retorno = false;
        }
        return retorno;
    }
    private void takePicture() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Log.i("TAG", "takePicture: " + photoFile);
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.wowguauv2",photoFile);
                    profile = photoURI;
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_RMASCOTAS);

                }
            }
        } else {
            Toast.makeText(this,"Sin acceso a camara",Toast.LENGTH_LONG).show();
            requestPermission(this, Manifest.permission.CAMERA,"Se necesita acceder a la camara", REQUEST_IMAGE_CAPTURE_RMASCOTAS);
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //////////////GALLERY
    private void addImage() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Intent pickImage = new Intent(Intent.ACTION_PICK);
            pickImage.setType("image/*");
            startActivityForResult(pickImage, REQUEST_IMAGE_PICKER_RMASCOTAS);
        } else {
            Toast.makeText(this, "Sin acceso al almacenamietno", Toast.LENGTH_LONG).show();
            requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE,"Se necesita acceder al almacenamiento", REQUEST_IMAGE_PICKER_RMASCOTAS);
        }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){

            case REQUEST_IMAGE_PICKER_RMASCOTAS:
                if (resultCode == RESULT_OK) {
                    try {
                        profile = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(profile);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imgVMascota.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case REQUEST_IMAGE_CAPTURE_RMASCOTAS:
                if (resultCode == RESULT_OK) {
                    File imgFile = new File(mCurrentPhotoPath);
                    if (imgFile.exists()) {
                        options = new BitmapFactory.Options();
                        options.inSampleSize = 16;
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getPath(),options);
                        imgVMascota.setImageBitmap(myBitmap);
                    }
                }
                break;
        }
    }
}
