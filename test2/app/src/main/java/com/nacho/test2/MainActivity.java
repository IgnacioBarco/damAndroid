package com.nacho.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private int puntuacionMaxima=0;
    ListView lista;
    GridView gl;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.lista);
        gl = findViewById(R.id.grid);
        spinner = findViewById(R.id.spinner);

        ArrayList<ObjetoFila> listaObjetos= new ArrayList<ObjetoFila>();

        listaObjetos.add(new ObjetoFila(R.drawable.mario,R.drawable.mario,"Mario"));
        listaObjetos.add(new ObjetoFila(R.drawable.mario,R.drawable.mario,"XXXXX"));
        listaObjetos.add(new ObjetoFila(R.drawable.mario,R.drawable.mario,"YYYYY"));

        AdaptadorPersonalizado ap = new AdaptadorPersonalizado(this,R.layout.linea_lista,listaObjetos);

        AdaptadorPersonalizado ap2 = new AdaptadorPersonalizado(this,R.layout.linea_lista,listaObjetos);

        AdaptadorPersonalizado ap3 = new AdaptadorPersonalizado(this,R.layout.linea_lista,listaObjetos);


        lista.setAdapter(ap);

        gl.setNumColumns(2);
        gl.setAdapter(ap2);

        spinner.setAdapter(ap3);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String nombre = "";
                if (i==0){ nombre="mario";}
                if (i==1){ nombre="xxxxx";}
                if (i==2){ nombre="yyyyy";}



                Toast.makeText(MainActivity.this, "Has pulsado "+nombre, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,ActividadJuego.class);

                intent.putExtra("jugador",nombre);

                startActivityForResult(intent,100);

            }
        });

        gl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                String nombre = "";
                if (i==0){ nombre="mario";}
                if (i==1){ nombre="xxxxx";}
                if (i==2){ nombre="yyyyy";}



                Toast.makeText(MainActivity.this, "Has pulsado "+nombre, Toast.LENGTH_SHORT).show();

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK){
            Toast.makeText(this, "Todo bien", Toast.LENGTH_SHORT).show();
        }
    }
}
