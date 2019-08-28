package com.example.proyecto;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nuevoPaseoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nuevoPaseoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nuevoPaseoFragment extends Fragment {

    Button btnConfirmarPaseo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        return inflater.inflate(R.layout.fragment_nuevopaseo, container, false);

    }

}
