package com.nacho.tarea02;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.RadioGroup;

/**
 * Created by Nacho on 12/03/2018.
 */

public class DialogoInstrucciones extends DialogFragment {

    //Este método es llamado al hacer el show() de la clase DialogFragment()
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Usamos la clase Builder para construir el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Escribimos el título
        builder.setTitle("Instrucciones");
        builder.setMessage("Autor: Nacho. \n\n\n" +
                "Se jugará hasta que se agoten las casillas del tablero. \n\n" +
                "Se puede comenzar en cualquier posición del tablero. \n\n" +
                "Cuando se juegue contra la máquina usted será el jugador 1.");

        return builder.create();
    }
}

