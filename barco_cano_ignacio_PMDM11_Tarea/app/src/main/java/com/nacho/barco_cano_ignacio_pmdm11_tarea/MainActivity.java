package com.nacho.barco_cano_ignacio_pmdm11_tarea;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import java.util.*;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button botonpulsado;
    int posicionMinima = 0;
    ArrayList<String> listado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonpulsado = ((Button) findViewById(R.id.boton));
        botonpulsado.setOnClickListener(this);

    }

    public void onClick(View view) {
        TextView camposolucion;
        EditText campobusqueda;
        String posicionstring;
        int posicion;
        String numPrimoString = "";
        int numPrimo = 0;
        String resultado = "";
        int i;
        int j;
        int k;
        int m;
        boolean primo;



        campobusqueda = (EditText) findViewById(R.id.editText2);
        camposolucion = (TextView) findViewById(R.id.textView4);
        posicionstring = campobusqueda.getText().toString();

        if (posicionMinima == 0) {
            listado.add("1");
            listado.add("2");
            listado.add("3");
            listado.add("5");
            listado.add("7");
            posicionMinima=5;
        }

        if (posicionstring.equals("")) {
            posicionstring = "0";
        }
        posicion = Integer.parseInt(posicionstring);

        if (posicion == 0) {
            camposolucion.setText("La posición no puede ser 0 o estar vacia, indique de nuevo una posición correcta");
            campobusqueda.setText("");
        } else {
            campobusqueda.setText("");
            posicionMinima=listado.size();
            if (posicion <= posicionMinima) {
                camposolucion.setText("El primo numero " +posicion+ " es " +listado.get(posicion-1));
            }

            if (posicion > posicionMinima) {
                for (i = listado.size(); i <= posicion - 1; i++) {
                    posicionMinima = listado.size();
                    numPrimo = Integer.parseInt(listado.get(posicionMinima - 1));

                   primo=false;
                   j=numPrimo+2;
                   k=3;

                        do {
                            if ((j%k == 0)){
                                if (j==k){primo=true;}
                                if (j!=k){j++;j++;k=3;}
                            } else{k++;k++;}
                        }while(!primo);

                    numPrimoString = Integer.toString(j);
                    listado.add(numPrimoString);
                    posicionMinima = listado.size();
                }
                camposolucion.setText("El primo numero " + posicion + " es " + numPrimoString);
            }
        }
    }

}




