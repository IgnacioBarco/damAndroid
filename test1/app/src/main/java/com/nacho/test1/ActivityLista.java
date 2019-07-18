package com.nacho.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityLista extends AppCompatActivity {

    private ListView lista;
    private AdaptadorPersonalizado adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lista=findViewById(R.id.lista);

        String[] datos = {"1","2","3","4","5"};
        adaptador=new AdaptadorPersonalizado(this,R.layout.activity_linea_lista,datos);

        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ActivityLista.this, ""+adaptador.dato[i], Toast.LENGTH_SHORT).show();

            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ActivityLista.this, "largoooo", Toast.LENGTH_SHORT).show();
                return false;
            }
        });



    }
}
