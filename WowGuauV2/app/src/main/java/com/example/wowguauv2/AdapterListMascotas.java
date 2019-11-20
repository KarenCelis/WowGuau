package com.example.wowguauv2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Blob;

public class AdapterListMascotas extends CursorAdapter {

    private static final int DYSPLAY_IMG_INDEX = 0;
    private static final int DISPLAY_NAME_INDEX = 1;

    public AdapterListMascotas(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_lista_mascotas,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imgVMascota = view.findViewById(R.id.imgVMascota);
        TextView txtvNombreM = view.findViewById(R.id.txtvNombreM);
        Bitmap image= BitmapFactory.decodeByteArray(cursor.getBlob(DYSPLAY_IMG_INDEX),0,cursor.getBlob(DISPLAY_NAME_INDEX).length);
        String nombre = cursor.getString(DISPLAY_NAME_INDEX);

        if(image!=null)
            imgVMascota.setImageBitmap(image);
        txtvNombreM.setText(nombre);
    }
}
