package com.nacho.listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactoAdapter adapter;
    private ListView contactos;
    private ArrayList<Contacto> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactos = findViewById(R.id.contactos);

        datos = new ArrayList<>();
        datos.add(new Contacto(2,"Juan","+34 678 987 345",R.drawable.ic_launcher_background));
        datos.add(new Contacto(1,"Luis","+34 768 987 345",R.drawable.ic_launcher_background));
        datos.add(new Contacto(3,"Pedro","+34 699 987 345",R.drawable.ic_launcher_background));
        datos.add(new Contacto(4,"Alvaro","+34 612 987 345",R.drawable.ic_launcher_background));
        datos.add(new Contacto(5,"Francisco","+34 698 987 345",R.drawable.ic_launcher_background));
        datos.add(new Contacto(6,"Jose","+34 654 987 345",R.drawable.ic_launcher_background));

        adapter = new ContactoAdapter(this,datos);

        contactos.setAdapter(adapter);

        contactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Contacto c = datos.get(pos);

                Intent intent = new Intent(MainActivity.this,DetalleContacto.class);
                intent.putExtra("id",c.getId());
                intent.putExtra("nombre",c.getNombre());
                intent.putExtra("telefono",c.getTelefono());
                intent.putExtra("imagen",c.getImagen());

                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100 && resultCode == RESULT_OK) {
            int id = data.getIntExtra("id",-1);
            String telefono = data.getStringExtra("telefono");
            String nombre = data.getStringExtra("nombre");
            int imagen = data.getIntExtra("imagen",-1);

            Contacto contactoEditado = new Contacto(id,nombre,telefono,imagen);
            int indice = datos.indexOf(contactoEditado);
            datos.set(indice,contactoEditado);

            adapter.notifyDataSetChanged();

            //notificacion
        }
    }
}
