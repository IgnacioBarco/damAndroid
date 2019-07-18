package com.nacho.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorPersonalizado extends ArrayAdapter<ObjetoFila>{

    public AdaptadorPersonalizado(Context ctx,
                                  int txtViewResourceId, List<ObjetoFila> objects){
        super(ctx, R.layout.linea_lista, objects);
    }
    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt){
        return crearFilaPersonalizada(position, cnvtView, prnt);
    }
    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt){
        return crearFilaPersonalizada(pos, cnvtView, prnt);
    }

    public View crearFilaPersonalizada(int position,
                                       View convertView, ViewGroup parent){

        ObjetoFila objeto = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View miFila = inflater.inflate(R.layout.linea_lista, parent, false);

        ImageView imagen1 = (ImageView) miFila.findViewById(R.id.imagen1);
        imagen1.setImageResource(objeto.getImagen1());

        TextView nombre = (TextView) miFila.findViewById(R.id.texto);
        nombre.setText(objeto.getTexto().toString());

        ImageView imagen2 = (ImageView) miFila.findViewById(R.id.imagen2);
        imagen2.setImageResource(objeto.getImagen2());

        return miFila;
    }
}
