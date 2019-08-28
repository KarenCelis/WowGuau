package com.example.proyecto;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CalendarioFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        return inflater.inflate(R.layout.fragment_calendario, container, false);
    }

}
