package com.example.wowguauv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context contexto;
    String [][] datos;

    public Adaptador(Context contexto, String[][] datos) {
        this.contexto = contexto;
        this.datos = datos;
        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.list_item_solicitudes, null);
        TextView nombre = (TextView) vista.findViewById(R.id.nombre_cliente);
        TextView distancia = (TextView) vista.findViewById(R.id.distancia);

        nombre.setText(datos[i][0]);
        distancia.setText(datos[i][1]);
        return vista;
    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
