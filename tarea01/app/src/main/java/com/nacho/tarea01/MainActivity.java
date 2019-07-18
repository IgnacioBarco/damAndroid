package com.nacho.tarea01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button boton;
    TextView campoResultado;
    EditText campoPosicion;
    CalculoNumerosPrimos cnp;
    int posicion;
    String sposicion;
    int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cnp=new CalculoNumerosPrimos();

        campoResultado = ((TextView)findViewById(R.id.resultado));
        campoPosicion = ((EditText)findViewById(R.id.numeroPosicion));
        boton = ((Button)findViewById(R.id.botonCalcular));
        boton.setOnClickListener(this);

        cnp.crearArray();
    }

    public void onClick(View view){

        sposicion = campoPosicion.getText().toString();

        if (sposicion.equals("")){
            campoResultado.setText("La posición no puede estar vacia");
        } else if (sposicion.equals("0")){
            campoResultado.setText("La posición no puede ser 0");
        } else {
            i = Integer.valueOf(sposicion);

            if (i<0){
                campoResultado.setText("La posición no puede ser negativa");
            }

            if (i>0 && i<=999999){
                campoResultado.setText(cnp.devolverResultado(i));
            } else if (i>999999){
                campoResultado.setText("No se pueden calcular numeros a partir de 6 digitos");
            }
        }


}

}
