package com.nacho.tarea08;

import android.content.Context;
import android.content.SharedPreferences;

public class PuntuacionesDB {

    //patron singleton
    private static PuntuacionesDB instance;

    private final String CLAVE_PUNTUACION = "id_puntuacion";
    private final SharedPreferences preferences;

    public static synchronized PuntuacionesDB getInstance(Context c){
        if(instance == null){
            instance = new PuntuacionesDB(c);
        }
        return instance;
    }

    private PuntuacionesDB(Context c){
        this.preferences = c.getSharedPreferences("puntuaciones",Context.MODE_PRIVATE);
    }

    public synchronized int getPuntuacionMaxima(){
        return preferences.getInt(CLAVE_PUNTUACION,0);
    }

    public synchronized void guardarPuntuacionMaxima(int puntuacion){
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt(CLAVE_PUNTUACION,puntuacion);
        preferencesEditor.apply();
    }

}
