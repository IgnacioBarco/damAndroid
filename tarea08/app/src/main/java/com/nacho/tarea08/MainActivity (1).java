package com.nacho.tarea08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PuntuacionesDB p = PuntuacionesDB.getInstance(this);








        p = null;
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
