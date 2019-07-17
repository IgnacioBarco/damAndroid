package com.nacho.asyntask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private Button calcular;
    private Button fechaPuntos;
    private EditText fechaNac;
    private EditText salario;
    private EditText numHijos;

    private int dia;
    private int mes;
    private int anyo;
    private int edad;
    private EditText retencion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcular = findViewById(R.id.calcular);
        fechaNac = findViewById(R.id.fechaNac);
        salario = findViewById(R.id.salario);
        numHijos = findViewById(R.id.numHijos);
        fechaPuntos = findViewById(R.id.fechaNacBt);
        retencion = findViewById(R.id.totalRetencion);

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CalculoActivity.class);
                intent.putExtra("dia",dia);
                intent.putExtra("mes",mes);
                intent.putExtra("anyo",anyo);

                String salarioTexto = MainActivity.this.salario.getText().toString();
                double salario = Double.parseDouble(salarioTexto);

                String numHijosTexto = numHijos.getText().toString();
                int numHijos = Integer.parseInt(numHijosTexto);

                intent.putExtra("salario",salario);
                intent.putExtra("numHijos",numHijos);

                startActivityForResult(intent,420);
            }
        });

        fechaPuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anyo, int mes, int dia) {
                        fechaNac.setText(dia + "/" + mes + "/" + anyo);
                        MainActivity.this.dia = dia;
                        MainActivity.this.mes = mes;
                        MainActivity.this.anyo = anyo;

                        Calendar c = Calendar.getInstance();

                        int anyoActual = c.get(Calendar.YEAR);
                        int mesActual = c.get(Calendar.MONTH);
                        int diaActual = c.get(Calendar.DAY_OF_MONTH);

                        int edad = anyoActual - anyo;

                        if(mesActual < mes) {
                            edad--;
                        }

                        else if(mesActual == mes && diaActual < dia) {
                            edad--;
                        }

                        MainActivity.this.edad = edad;


                    }
                },2018,8,15);
                dialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 420 && resultCode == RESULT_OK) {
            String resultado = data.getStringExtra("resultado");
            retencion.setText(resultado);
        }
    }
}
