package com.nacho.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactoAdapter extends ArrayAdapter<Contacto> {

    public ContactoAdapter(@NonNull Context context, @NonNull List<Contacto> objects) {
        super(context, R.layout.contacto_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View vistaReciclada, @NonNull ViewGroup parent) {
       Contacto c = getItem(position);

       if(vistaReciclada == null) {
           LayoutInflater inflater = LayoutInflater.from(getContext());
           vistaReciclada = inflater.inflate(R.layout.contacto_item,parent,false);
       }

        ImageView fotoContacto = vistaReciclada.findViewById(R.id.foto_contacto);
        TextView nombreContacto = vistaReciclada.findViewById(R.id.nombre_contacto);

        fotoContacto.setImageResource(c.getImagen());
        nombreContacto.setText(c.getNombre());

        return vistaReciclada;
    }
}
