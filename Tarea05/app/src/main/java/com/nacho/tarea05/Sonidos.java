package com.nacho.tarea05;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sonidos {

    private SoundPool soundPool;

    private int idOk;
    private int idError;
    private int idPerdida;
    private int idPulsarNada;
    private int idOkGirando;


    public Sonidos(Context context){

        //creamos el soundpool
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC,0);

        //generamps el sonido
        idOk = soundPool.load(context,R.raw.ok,0);
        idError = soundPool.load(context,R.raw.error,0);
        idPerdida = soundPool.load(context,R.raw.figuraperdida,0);
        idPulsarNada = soundPool.load(context,R.raw.nadapulsado,0);
        idOkGirando = soundPool.load(context,R.raw.girandomascolor,0);
    }

    public void playOk(){
        soundPool.play(idOk,1f,1f,0,0,1);
    }

    public void playError(){
        soundPool.play(idError,1f,1f,0,0,1);
    }

    public void playPerdida(){
        soundPool.play(idPerdida,1f,1f,0,0,1);
    }

    public void playPulsarNada(){
        soundPool.play(idPulsarNada,1f,1f,0,0,1);
    }

    public void playOkGirando(){
        soundPool.play(idOkGirando,1f,1f,0,0,1);
    }




}
