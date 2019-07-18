package com.nacho.tarea08;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final String TAG = Juego.class.getSimpleName();
    private final int MOVER_IZQ = 1;
    private final int MOVER_DER = 2;
    private SurfaceHolder holder;
    private BucleJuego bucle;
    private DisplayMetrics metricasPantalla;
    private ArrayList<Cuadrado> listaCuadrados;
    private Paint paint;
    private Random r;
    private Cuadrado nave;
    private int movimiento = -1;
    private int puntuacion = 0;
    private boolean gameOver;

    private PuntuacionesDB puntuacionesDB;

    public Juego(Context context) {
        super(context);
        puntuacionesDB = new PuntuacionesDB(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    //por si rortase la pantalla
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }




    private void crearListaCuadrados(int color) {

        Boolean ver=true;

        //crear los 5 cuadrados
        for (int i = 0; i < 5; i++) {
            ver=true;
            Cuadrado c = new Cuadrado(color,
                    metricasPantalla.widthPixels/30,
                    metricasPantalla.heightPixels/50,
                    r.nextInt(metricasPantalla.widthPixels-metricasPantalla.widthPixels/30),
                    r.nextInt(metricasPantalla.heightPixels-metricasPantalla.heightPixels/50));

            for(Cuadrado cu : listaCuadrados){

                if (c.intersect(cu)){
                    ver = false;
                }
            }

            if (!ver) {
                c=null;
            } else {
                listaCuadrados.add(c);
            }


        }
    }


    private void crearListaCuadrados() {

        crearListaCuadrados(Color.YELLOW);

        crearListaCuadrados(Color.RED);

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


        //crear las figuras


        setOnTouchListener(this);

        //para capturar las medidas de la pantalla
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        metricasPantalla = new DisplayMetrics();
        display.getMetrics(metricasPantalla);

        listaCuadrados = new ArrayList<Cuadrado>();
        paint =new Paint();
        r = new Random();


        nave = new Cuadrado(
                Color.GREEN,
                metricasPantalla.widthPixels/25,
                metricasPantalla.heightPixels/45,
                (metricasPantalla.widthPixels/2)-(metricasPantalla.widthPixels/25),
                metricasPantalla.heightPixels-50);
        //crear nave
        listaCuadrados.add(nave);//50 xq tiene botones virtuales

        crearListaCuadrados();

        paint.setTextSize(20f);


        //comenzar el bucle
        bucle.start();


    }


    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {

        //+10 pixeles
        //actualizar posiciones
        nave.subir(10);

        if (movimiento == MOVER_DER) {
            nave.desplazarHorizontal(+10);
        } else if (movimiento == MOVER_IZQ) {
            nave.desplazarHorizontal(-10);
        }

        movimiento = -1;


        //miramos los choques
        List<Cuadrado> eliminar = new ArrayList<>();

        for (Cuadrado c : listaCuadrados) {
            if (!c.equals(nave) && c.intersect(nave)) {
                if (c.getColor() == Color.YELLOW) {
                    puntuacion += 10;
                    eliminar.add(c);
                } else if (c.getColor() == Color.RED) {
                    gameOver = true;
                    break;
                }

            }
        }

        listaCuadrados.removeAll(eliminar);


        //verificamos si se ha acabado
        if(nave.getRect().top <= 0) {
            gameOver = true;
        }

        if(gameOver)  {
            //guardamos la puntuiacion máxima
            if(puntuacion>puntuacionesDB.getPuntuacionMaxima()){
                puntuacionesDB.guardarPuntuacionMaxima(puntuacion);
            }
            bucle.parar();
        }

    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {

        //limpiar pantalla
        canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);

        //pintar cuadrados y nave
        for (Cuadrado c: listaCuadrados) {

            c.renderizar(canvas);

        }

        //por si acaso, que no afecte el color del pincel
        int colorActual = paint.getColor();



        //pintamos la puntuacion
        paint.setColor(Color.RED);
        canvas.drawText(String.valueOf(puntuacion),metricasPantalla.widthPixels-50,50,paint);

        //si game over pintamos
        if(gameOver) {
            canvas.drawText("GAME OVER",metricasPantalla.widthPixels/2,metricasPantalla.heightPixels/2,paint);
        }


        //volvemos a poner el color del pincel
        paint.setColor(colorActual);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Juego destruido!");
        // cerrar el thread y esperar que acabe

        //para salir de la aplicacion y que no falle
        bucle.parar();


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

        float x = motionEvent.getX();
        float y = motionEvent.getY();

        Rect rect =nave.getRect();

        int centroNave = rect.centerX();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                synchronized (this) {

                    if(x < centroNave) { //izq
                        movimiento = MOVER_IZQ;

                }
                else { //der
                        movimiento = MOVER_DER;

                    }

                }
                break;
        }
        return true;
    }
}
