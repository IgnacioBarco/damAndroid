package com.nacho.test2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class ActividadJuego extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jugador = getIntent().getStringExtra("jugador");
        setContentView(new Juego(this,jugador));


    }



}
