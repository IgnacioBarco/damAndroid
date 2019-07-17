package com.nacho.asyntask;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CalculoActivity extends AppCompatActivity {

    private FloatingActionButton guardar;
    private ListView lista;
    private ArrayList<String> calculos;
    private ArrayAdapter<String> adapter;
    private int numHijos;
    private double salario;
    private int dia;
    private int mes;
    private int anyo;

    private String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);

        lista = findViewById(R.id.lista);
        guardar = findViewById(R.id.guardar);

        Intent intent = getIntent();

        dia = intent.getIntExtra("dia",-1);
        mes = intent.getIntExtra("mes",-1);
        anyo = intent.getIntExtra("anyo",-1);

        salario = intent.getDoubleExtra("salario",-1);
        numHijos = intent.getIntExtra("numHijos",-1);

        calculos = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,calculos);

        lista.setAdapter(adapter);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();

                intent1.putExtra("resultado",resultado);

                setResult(RESULT_OK,intent1);
                //para matar la activity
                finish();
            }
        });

        new CalculosAsyncTask().execute();

    }


    class CalculosAsyncTask extends AsyncTask<Void,String,String> {


        /**
         * Nos sirve para iniciar cualquier dato o vista que necesitemos
         * antes de ejecutar la tarea (SE EJECUTA EN EL HILO PRINCIPAL)
         */
        @Override
        protected void onPreExecute() {

        }

        /*
        Este metodo se va a ejecutar en el hilo secundario, aqui realizamos
        la tarea costosa, se llama a continuacion del onPreExecute()
         */
        @Override
        protected String doInBackground(Void[] objects) {

            String texto = "Salario bruto "+salario;

            publishProgress(texto);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            texto = "Deduccion SS "+(salario-400);

            publishProgress(texto);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Total final: 532";
        }

        /*
        Se ejecuta en el hilo principal, nos sirve para actualizar la  pantalla mientras esta
        ejecutandose la tarea (a traves del publishProgress)
         */
        @Override
        protected void onProgressUpdate(String[] values) {
            calculos.add(values[0]);
            adapter.notifyDataSetChanged();
        }

          /*
            Se ejecuta en el hilo principal
        */
        @Override
        protected void onPostExecute(String resultado) {
          calculos.add(resultado);
          adapter.notifyDataSetChanged();
          CalculoActivity.this.resultado = resultado;
        }
    }
}
