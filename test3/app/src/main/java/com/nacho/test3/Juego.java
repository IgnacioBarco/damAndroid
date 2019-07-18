package com.nacho.test3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private SurfaceHolder holder;
    private BucleJuego bucle;
    private boolean clicArriba; //false
    private boolean clickAbajo;
    private boolean cuadrado; //false
    private int color; //0

    private static final String TAG = Juego.class.getSimpleName();
    private Rect rect;
    private int radioCirculo;

    private Paint paint;
    private int mitadX;
    private int mitadY;

    public Juego(Context context) {
        super(context);
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

        color = Color.BLACK;

        paint = new Paint();

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE) ;

        Activity activity = (Activity) getContext();
        windowManager = activity.getWindowManager();

        Display mdisp = windowManager.getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);

        int maxX = mdispSize.x;
        int maxY = mdispSize.y;

        int lado = 100;

        mitadX = (maxX / 2);
        mitadY = (maxY / 2);

        int left = mitadX - (lado/2);
        int top = mitadY - (lado/2);
        int bottom = mitadY + (lado/2);
        int right = mitadX + (lado/2);

        rect = new Rect(left,top,right,bottom);

        radioCirculo = lado/2;

        setOnTouchListener(this);

        // Pintar circulo, negro en un inicio

        //comenzar el bucle
        bucle.start();

    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {
        if(clicArriba) {
            cuadrado = !cuadrado;
            clicArriba = false;
        }
        else if(clickAbajo) {
            if(color == Color.RED) {
                color = Color.YELLOW;
            }
            else {
                color = Color.RED;
            }
            clickAbajo = false;
        }
    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        paint.setColor(color);
        if(cuadrado) {
            canvas.drawRect(rect,paint);
        }
        else {
            canvas.drawCircle(rect.centerX(),rect.centerY(),radioCirculo,paint);
        }
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
        int y = (int) motionEvent.getY();

        int action = motionEvent.getAction();

        if(action == MotionEvent.ACTION_DOWN) {
            Toast.makeText(getContext(), "Clic en la pantalla", Toast.LENGTH_SHORT).show();
        }
        else if(action == MotionEvent.ACTION_UP) {
            Toast.makeText(getContext(), "Dedo levantado de la pantalla", Toast.LENGTH_SHORT).show();
        }

        if(y > mitadY) {
            clicArriba = false;
            clickAbajo = true;
        }
        else {
            clicArriba = true;
            clickAbajo = false;
        }



        return false;
    }
}
