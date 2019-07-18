package com.nacho.tarea06;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private LinearLayout linear;
    private SensorManager sensorManager;
    private List<Sensor> listadoSensores;
    private TextView tve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linear = findViewById(R.id.lyLinear);

        //pasamos los datos a una lista
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        listadoSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);

        generarListado();
    }


    private void generarListado() {

        for (Sensor sensor : listadoSensores) {

            TextView tv = new TextView(this);
            String nombreSensor = sensor.getName();
            tv.setText(nombreSensor);

            tv.setLayoutParams(new ViewGroup.LayoutParams(

                    ViewGroup.LayoutParams.WRAP_CONTENT,

                    ViewGroup.LayoutParams.WRAP_CONTENT));
//gitlab - bitbucket

            /*para quitar el error que me salia
            if(tv.getParent()!=null)
              ((ViewGroup)tv.getParent()).removeView(tv); */

            //identificamos el textview con el tipo de sensor +5000
            tv.setId(sensor.getType() + 5000);

            //añadimos el tv al linearLayout
            linear.addView(tv);
        }
        ;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //buscamos el sensor que ha cambiado
        tve = findViewById(sensorEvent.sensor.getType() + 5000);
        String valores = "";

        //hacemos un bucle para capturar todos los datos
        //ya que algunos sensores traen mas de 1 parametro
        for (int i = 0; i < sensorEvent.values.length; i++) {
            valores += sensorEvent.values[i] + " | ";
        }
        tve.setText(sensorEvent.sensor.getName() + ": " + valores);
   }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        //quitamos el listener
        sensorManager.unregisterListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        //ponemos listener a todos los sensores que hay en el listado
        for (Sensor sensor : listadoSensores)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
