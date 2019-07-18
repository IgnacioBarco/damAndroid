package com.nacho.tarea05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private Button jugar;
    private RadioGroup colores;
    private RadioGroup figuras;
    private RadioGroup tiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //definimos los radiogroups
        jugar = findViewById(R.id.btJugar);
        colores = findViewById(R.id.rgColor);
        figuras = findViewById(R.id.rgFiguras);
        tiempo = findViewById(R.id.rgTiempo);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarJuego();
            }
        });
    }

    //al pulsar el boton
    private void lanzarJuego() {
        Intent eventoJuego = new Intent(this,ActividadJuego.class);
        eventoJuego.putExtra("color",getColorSeleccionado());
        eventoJuego.putExtra("figura",getFiguraSeleccionada());
        eventoJuego.putExtra("tiempo",getTiempoSeleccionado());
        startActivity(eventoJuego);
    }

    public String getColorSeleccionado() {
        switch (colores.getCheckedRadioButtonId()){
            case R.id.rbAzul:
                return "azul";
            case R.id.rbBlanco:
                return "blanco";
            case R.id.rbRojo:
                return "rojo";
        }
        return null;
    }

    public String getFiguraSeleccionada() {
        switch (figuras.getCheckedRadioButtonId()){
            case R.id.rbRectangulo:
                return "rectangulo";
            case R.id.rbCuadrado:
                return "cuadrado";
            case R.id.rbTriangulo:
                return "triangulo";
        }
        return null;
    }

    public String getTiempoSeleccionado() {
        switch (tiempo.getCheckedRadioButtonId()){
            case R.id.rbSeg30:
                return "30";
            case R.id.rbSeg40:
                return "40";
            case R.id.rbSeg50:
                return "50";
        }
        return null;

    }
}
