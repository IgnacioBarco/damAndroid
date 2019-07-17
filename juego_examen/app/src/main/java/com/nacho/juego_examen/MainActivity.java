package com.nacho.juego_examen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button jugar;
    private TextView puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jugar = findViewById(R.id.jugar);
        puntuacion = findViewById(R.id.resultado);

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAJuego();
            }
        });

    }

    private void irAJuego() {
        Intent intent = new Intent(this,ActividadJuego.class);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == 200 && resultCode == RESULT_OK) {
           int toques = data.getIntExtra("toques",-1);
           puntuacion.setText(String.valueOf(toques));
       }
    }
}
