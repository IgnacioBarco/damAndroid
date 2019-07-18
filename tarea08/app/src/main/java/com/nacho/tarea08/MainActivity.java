package com.nacho.tarea08;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button iniciarJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //para guardar las puntuaciones
        PuntuacionesDB p = new PuntuacionesDB(this);


        iniciarJuego = findViewById(R.id.button);

        //despues de instanciar el texto
        //xxx.setText(p.getPuntuacionMaxima());

        iniciarJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActividadJuego.class);
                startActivity(intent);
            }
        });
    }

    @Override
    //crear nuevo resource tipo menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salir:
                finish();
                break;
            case R.id.acerca_de:
                Toast.makeText(this, "lalalalallaal", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


}
