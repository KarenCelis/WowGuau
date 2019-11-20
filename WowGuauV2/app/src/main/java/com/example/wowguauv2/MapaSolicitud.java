package com.example.wowguauv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MapaSolicitud extends AppCompatActivity implements MapasFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_solicitud);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        Double lat = bundle.getDouble("lat");
        Double lon = bundle.getDouble("lon");

        Fragment fragmento = new MapasFragment();
        Bundle bundle1 = new Bundle();
        bundle.putDouble("lat",lat);
        bundle.putDouble("lon",lon);
        fragmento.setArguments(bundle);
        Log.i("TAG", "Pos " + lat + "   " + lon);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragmento).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
