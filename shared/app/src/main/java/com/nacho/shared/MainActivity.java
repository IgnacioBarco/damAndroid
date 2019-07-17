package com.nacho.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button contarVisita;
    private TextView contador;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private int visitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contador = findViewById(R.id.visitas);
        contarVisita = findViewById(R.id.boton);

        preferences = getPreferences(Context.MODE_PRIVATE);

        editor = preferences.edit();

        visitas = preferences.getInt("num_visitas",0);

        contador.setText(String.valueOf(visitas));

        contarVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aumentarVisita();
            }
        });
    }

    private void aumentarVisita() {
        visitas++;
        contador.setText(String.valueOf(visitas));
        editor.putInt("num_visitas",visitas);
        editor.apply();
    }
}
