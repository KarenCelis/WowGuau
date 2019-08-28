package com.example.proyecto;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorialPaseosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialPaseosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialPaseosFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        return inflater.inflate(R.layout.fragment_historialpaseos, container, false);
    }

   
}
