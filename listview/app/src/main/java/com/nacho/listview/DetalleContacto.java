package com.nacho.listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetalleContacto extends AppCompatActivity {

    private EditText nombre;
    private EditText telefono;
    private Button guardar;

    private Contacto contactoEdicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);

        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);

        guardar = findViewById(R.id.guardar);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        String telefono = intent.getStringExtra("telefono");
        String nombre = intent.getStringExtra("nombre");
        int imagen = intent.getIntExtra("imagen",-1);

        contactoEdicion = new Contacto(id,nombre,telefono,imagen);

        this.nombre.setText(contactoEdicion.getNombre());
        this.telefono.setText(contactoEdicion.getTelefono());

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.putExtra("id",contactoEdicion.getId());
                intent.putExtra("nombre",contactoEdicion.getNombre());
                intent.putExtra("telefono",contactoEdicion.getTelefono());
                intent.putExtra("imagen",contactoEdicion.getImagen());

                setResult(RESULT_OK,intent);
            }
        });
    }
}
