package com.nacho.test2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class Juego extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private BucleJuego bucle;
    private String jugador;
    private int AnchoPantalla;
    private int AltoPantalla;
    private Display display;
    private Bitmap imagen_escalada;
    private Bitmap imagen_escalada2;



    private static final String TAG = Juego.class.getSimpleName();

    public Juego(Context context, String jugador) {
        super(context);
        this.jugador=jugador;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);

        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metricasPantalla = new DisplayMetrics();
        display.getMetrics(metricasPantalla);
        AltoPantalla = metricasPantalla.heightPixels;
        AnchoPantalla = metricasPantalla.widthPixels;

        Bitmap imagen = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
        imagen_escalada = imagen.createScaledBitmap(imagen, AnchoPantalla/8, AltoPantalla/8, true);







        //comenzar el bucle
        bucle.start();

    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {
        //
    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {

        canvas.drawBitmap(imagen_escalada,AnchoPantalla/2,AltoPantalla/2,null);

        //canvas.drawBitmap(imagen_escalada,null,rect,null);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Juego destruido!");
        // cerrar el thread y esperar que acabe
        boolean retry = true;
        while (retry) {
            try {
                bucle.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }


}
