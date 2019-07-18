package com.nacho.test1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityAsync extends AppCompatActivity {

    private String datoInicial;
    private ListView listaAsync;
    private ArrayList<String> elementos;
    private ArrayAdapter<String> adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        listaAsync = findViewById(R.id.listaAsync);

        elementos = new ArrayList<String>();

        elementos.add("aaaa");
        elementos.add("bbbb");

        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,elementos);

        listaAsync.setAdapter(adaptador);

        datoInicial=getIntent().getStringExtra("datoEnviado");

        elementos.add("cccc");
        adaptador.notifyDataSetChanged();


        Toast.makeText(this, "nos envian: "+datoInicial, Toast.LENGTH_SHORT).show();

        new tareaAsync().execute();



    }


    class tareaAsync extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            publishProgress("primer dato");

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            publishProgress("este el el segundo dato");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onProgressUpdate(String[] values) {
            elementos.add(values[0]);
            adaptador.notifyDataSetChanged();
        }



        @Override
        protected void onPostExecute(String s) {

            Intent intent4 = new Intent();
            intent4.putExtra("resultado", "fin de la asyntask");
            setResult(RESULT_OK,intent4);
            finish();

        }
    }
}


