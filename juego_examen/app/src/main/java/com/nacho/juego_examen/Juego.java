package com.nacho.juego_examen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private SurfaceHolder holder;
    private BucleJuego bucle;

    private Bitmap pelota;
    private Bitmap campo;

    private Paint paint;

    private static final String TAG = Juego.class.getSimpleName();
    private int ancho;
    private int alto;

    private int mitadPantallaAncho;
    private int mitadPantallaAlto;
    private int anchoPelota;
    private Rect cuadradoPelota;

    private int toques;

    private boolean haciaArriba = true;

    public Juego(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        pelota = BitmapFactory.decodeResource(context.getResources(),R.drawable.soccerball);
        campo = BitmapFactory.decodeResource(context.getResources(),R.drawable.football_pitch);
        paint = new Paint();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        ancho = display.getWidth();
        alto = display.getHeight();

        DisplayMetrics metricasPantalla = new DisplayMetrics();
        /*
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
         */
        display.getMetrics(metricasPantalla);

        ancho = metricasPantalla.widthPixels;
        alto = metricasPantalla.heightPixels;

        mitadPantallaAncho = ancho / 2;
        mitadPantallaAlto = alto / 2;

        anchoPelota = pelota.getWidth();

        int mitadAnchoPelota = anchoPelota / 2;

        int left = mitadPantallaAncho - mitadAnchoPelota;
        int top = mitadPantallaAlto - mitadAnchoPelota;
        int right = mitadPantallaAncho + mitadAnchoPelota;
        int bottom = mitadPantallaAlto + mitadAnchoPelota;

        cuadradoPelota = new Rect(left,top,right,bottom);

        campo = Bitmap.createScaledBitmap(campo,ancho,alto,true);



        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);

        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);

        setOnTouchListener(this);


        //comenzar el bucle
        bucle.start();

    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {
        int aumento = 10;
        int top;
        int bottom;
        if(haciaArriba) {
            top = cuadradoPelota.top - aumento;
            bottom = cuadradoPelota.bottom - aumento;
        }
        else {
            top = cuadradoPelota.top + aumento;
            bottom = cuadradoPelota.bottom + aumento;
        }
        cuadradoPelota.set(cuadradoPelota.left,top,cuadradoPelota.right,bottom);

        if(top < 0 || bottom > (mitadPantallaAlto*2)) {
            bucle.JuegoEnEjecucion = false;
            Activity activity = (Activity) getContext();

            Intent datos = new Intent();

            datos.putExtra("toques",toques);

            activity.setResult(Activity.RESULT_OK,datos);
            activity.finish();
        }
    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {
        canvas.drawBitmap(campo,0,0,paint);
        canvas.drawBitmap(pelota,null,cuadradoPelota,paint);
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


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && cuadradoPelota.contains(x,y)) {
            haciaArriba = !haciaArriba;
            toques++;
        }
        return true;
    }
}
