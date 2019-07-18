package com.nacho.tarea05;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class ActividadJuego extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //capturamos los datos pasados
        Intent intentInicio = getIntent();
        String figuraSeleccionada = intentInicio.getStringExtra("figura");
        String colorSeleccionado = intentInicio.getStringExtra("color");
        String tiempoSeleccionado = intentInicio.getStringExtra("tiempo");

        //ponemos la puntuación en la barra
        getSupportActionBar().setTitle("Puntuacion obtenida:  0");

        setContentView(new Juego(this,figuraSeleccionada,colorSeleccionado,tiempoSeleccionado));
    }
}
