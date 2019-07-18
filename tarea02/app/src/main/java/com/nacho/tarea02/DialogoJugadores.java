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

public class DialogoJugadores extends DialogFragment {

    RespuestaDialogoJugadores respuesta;

    //Este método es llamado al hacer el show() de la clase DialogFragment()
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Usamos la clase Builder para construir el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Escribimos el título
        builder.setTitle("Pregunta muy importante:");
        builder.setView(R.layout.dialogo_jugadores);
        //Escribimos la pregunta
        builder.setMessage("¿CUANTOS JUGADORES VAIS A JUGAR?");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RadioGroup rg = getDialog().findViewById(R.id.seleccionJugadores);
                int idSel = rg.getCheckedRadioButtonId();
                if (idSel == R.id.radioButton){
                    respuesta.onRespuesta(1);
                }
                else {
                    respuesta.onRespuesta(2);
                }
            }
        });
        //añadimos el botón de Si y su acción asociada
        // Crear el AlertDialog y devolverlo
        return builder.create();
    }

    //interfaz para la comunicación entre la Actividad y el Fragmento
    public interface RespuestaDialogoJugadores {
        public void onRespuesta(int numJugadores);
    }

    //Se invoca cuando el fragmento se añade a la actividad
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        respuesta = (RespuestaDialogoJugadores) activity;
    }
}

