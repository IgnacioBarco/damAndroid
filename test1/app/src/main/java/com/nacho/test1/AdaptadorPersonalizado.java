package com.nacho.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorPersonalizado extends ArrayAdapter<String> {

    String[] dato = {"aaa","bbb","ccc","ddd","eee"};
    String[] descripciones = {"aaa","bbb","ccc","ddd","eee"};
    int imagenes[] = {R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background};


    public AdaptadorPersonalizado(Context ctx,
                                  int txtViewResourceId, String[] objects) {
        super(ctx, txtViewResourceId, objects);
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return crearFilaPersonalizada(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return crearFilaPersonalizada(pos, cnvtView, prnt);
    }

    public View crearFilaPersonalizada(int position,
                                       View convertView, ViewGroup parent) {

        //esto cambia!!!!!!!!!!!!
        //LayoutInflater inflater = getLayoutInflater();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View miFila = inflater.inflate(R.layout.activity_linea_lista, parent, false);

        TextView nombre = (TextView) miFila.findViewById(R.id.dato1);
        nombre.setText(dato[position]);

        TextView descripcion = (TextView) miFila.findViewById(R.id.dato2);
        descripcion.setText(descripciones[position]);

        ImageView imagen = (ImageView) miFila.findViewById(R.id.imagen);
        imagen.setImageResource(imagenes[position]);

        return miFila;
    }
}