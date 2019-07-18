package com.nacho.tarea08;

import android.content.Context;
import android.content.SharedPreferences;

public class PuntuacionesDB {

    private final String CLAVE_PUNTUACION = "id_puntuacion";
    private final SharedPreferences preferences;

    public PuntuacionesDB(Context c){
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
