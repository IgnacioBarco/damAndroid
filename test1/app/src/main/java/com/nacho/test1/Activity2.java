package com.nacho.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    private Button volver;
    private TextView contador2;
    private int numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        volver = findViewById(R.id.botonVolver);
        contador2=findViewById(R.id.contador2);

        numero = getIntent().getIntExtra("contador",-1);
        contador2.setText(""+numero);


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                numero++;
                intent1.putExtra("contador",numero);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });


    }
}
