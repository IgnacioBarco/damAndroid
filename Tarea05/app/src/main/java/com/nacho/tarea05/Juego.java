package com.nacho.tarea05;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.nacho.tarea05.figuras.Cuadrado;
import com.nacho.tarea05.figuras.Figura;
import com.nacho.tarea05.figuras.GenerarFiguraAleatoria;
import com.nacho.tarea05.figuras.Rectangulo;
import com.nacho.tarea05.figuras.Triangulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final String TAG = Juego.class.getSimpleName();
    private SurfaceHolder holder;
    private BucleJuego bucle;
    private Sonidos sonido;

    // primera en salir (esta en la parte inferior), en la mitad la segunda, y la tercera al principio
    private List<Figura> figurasPantalla;
    //cualquier clase que extienda de figura
    private Class<? extends Figura> figuraBuscada;
    private GenerarFiguraAleatoria generarFiguraAleatoria;
    private int colorBuscado;
    private Paint pincelTexto;
    private Paint paint;


    private long tiempoUltimaFigura;
    private long tiempoEntreFiguras;
    private long tiempo;
    private int segundosRestantes;

    private int altoPantalla;
    private int velocidad;
    private int puntuacion = 0;
    private CountDownTimer cuentaAtras;


    public Juego(Context context, String figuraSeleccionada, String colorSeleccionado, String tiempoSelecionado) {
        super(context);

        //capturamos las opciones de la panatalla principas
        figuraBuscada = getFigura(figuraSeleccionada);
        colorBuscado = getColor(colorSeleccionado);
        tiempo = getTiempo(tiempoSelecionado);


        //sincronizamos el array por que al meterle el sonido nos da fallo
        figurasPantalla = Collections.synchronizedList(new ArrayList<Figura>());
        holder = getHolder();
        holder.addCallback(this);


        //creamos la cuenta atras
        cuentaAtras = new CountDownTimer(tiempo, 1000) {
            @Override
            public void onTick(long millisRestantes) {
                segundosRestantes = (int) (millisRestantes / 1000.0);
            }

            @Override
            public void onFinish() {
                segundosRestantes = 0;
                //paramos el juego
                bucle.setJuegoEnEjecucion(false);
                //creamos un dialog para mostrar la puntuación
                AlertDialog dialogoPuntuacion = new AlertDialog.Builder(getContext())
                        .setTitle("Puntuacion obtenida")
                        .setMessage("" + puntuacion)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                Activity activity = (Activity) getContext();
                                activity.finish();
                            }
                        })
                        .show();
            }
        };
    }

    /**
     * capturamos las opciones del jugador
     * @param tiempoSelecionado opcion elegida
     * @return tiempo de juego
     */
    private long getTiempo(String tiempoSelecionado) {
        switch (tiempoSelecionado) {
            case "30":
                return 32000L;
            case "40":
                return 42000L;
            case "50":
                return 52000L;
        }
        return 32000L;
    }

    /**
     * capturamos las opciones del jugador
     * @param colorSeleccionado opcion elegida
     * @return color elegido
     */
    private int getColor(String colorSeleccionado) {
        switch (colorSeleccionado) {
            case "blanco":
                return Color.WHITE;
            case "rojo":
                return Color.RED;
            case "azul":
                return Color.BLUE;
        }
        return 0;
    }

    /**
     * capturamos las opciones del jugador
     * @param figuraSeleccionada opcion elegida
     * @return figura elegida
     */
    private Class getFigura(String figuraSeleccionada) {
        switch (figuraSeleccionada) {
            case "rectangulo":
                return Rectangulo.class;
            case "cuadrado":
                return Cuadrado.class;
            case "triangulo":
                return Triangulo.class;
        }
        return null;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        //creamos el objeto para gestionar los sonidos
        sonido = new Sonidos(getContext());

        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);

        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);

        //para pintar las figuras
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        //para poner el tiempo restante
        pincelTexto = new Paint();
        pincelTexto.setTextSize(50);
        pincelTexto.setColor(Color.WHITE);

        //ponemos el listener para los eventos
        setOnTouchListener(this);

        //para capturar las medidas de la pantalla
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metricasPantalla = new DisplayMetrics();
        display.getMetrics(metricasPantalla);

        //creamos una figura aleatoria y la metemos en el array
        generarFiguraAleatoria = new GenerarFiguraAleatoria(metricasPantalla);
        figurasPantalla.add(generarFiguraAleatoria.generarFigura());

        //definimos variables
        velocidad = 10;
        tiempoEntreFiguras=2500;
        altoPantalla = metricasPantalla.heightPixels;

        //capturamos el tiempo actual
        tiempoUltimaFigura = System.currentTimeMillis();

        //comenzar el bucle
        bucle.start();

        //comenzamos la cuenta atras
        cuentaAtras.start();

    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {

        long diferenciaTiempo = System.currentTimeMillis() - tiempoUltimaFigura;
        if (diferenciaTiempo >= tiempoEntreFiguras) {
            figurasPantalla.add(generarFiguraAleatoria.generarFigura());
            tiempoUltimaFigura = System.currentTimeMillis();
            //aumentamos la velocidad de caida para que los ultimos segundos vayan mas rapidas
            velocidad++;
        }

        //si hay mas de una figura
        if (figurasPantalla.size() > 0) {
            Figura primeraFigura = figurasPantalla.get(0);
            boolean val = false;
            //Comprueba la figura que esta mas abajo(la primera del array)
            // elimina si esta fuera y añade otra
            if (primeraFigura.isFueraPantalla(altoPantalla)) {
                val = false;
                //si es la figura que buscamos, si estta girando quitamos 10 puntos
                //y si no gira 1 punto
                if (primeraFigura.getClass().getSimpleName().startsWith(figuraBuscada.getSimpleName())) {
                    if (primeraFigura.getClass().getSimpleName().endsWith("Girando")) {
                        puntuacion -= 10;
                    } else {
                        puntuacion--;
                    }
                    val = true;
                    actualizarPuntuacion();
                    //simplemente para controlar por consola
                    // System.out.println("Se ha salido la figura buscada");
                }

                //si era la figura perdida emitimos un sonido de error
                if (val) {
                    sonido.playPerdida();
                }

                //eliminamos la figura sea o no la solicitada
                figurasPantalla.remove(0);
            }

            //hacemos que todas las figuras bajen "velocidad"
            for (Figura figura : figurasPantalla) {
                figura.descender(velocidad);
            }

            //restamos un poco el tiempo que pasa entre que salgan unas figura y otras
            tiempoEntreFiguras--;
        }


    }


    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {
        //para que no falle al crear la aplicación cuando no sea null que haga
        //pinta el canvas de negro y pinta todas las figuras del array
        if (canvas != null) {
            canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
            for (Figura figura : figurasPantalla) {
                figura.renderizar(canvas);
            }
            //pintamos el tiempo restante
            canvas.drawText(String.valueOf(segundosRestantes - 1), 20, 50, pincelTexto);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Juego destruido!");
        // cerrar el thread y esperar que acabe
        bucle.setJuegoEnEjecucion(false);
        boolean retry = true;
        while (retry) {
            try {
                bucle.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     * actualizamos la puntuacion en la barra
     */
    private void actualizarPuntuacion() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
        appCompatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActionBar barraSuperior = ((AppCompatActivity) getContext()).getSupportActionBar();
                barraSuperior.setTitle("Puntuacion actual:  " + puntuacion);
            }
        });
    }


    @Override
    /**
     * evento toque
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //click en un punto de la pantalla
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            //capturamos la posicion del toque
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            boolean ver = false;
            boolean ver2 = false;
            int puntuacionPulsacion = 0;
            Figura figura = null;

            //en cada figura chequeamos el toque
            for (Figura f : figurasPantalla) {
                puntuacionPulsacion = 0;
                ver = false;
                ver2 = false;

                //si pulsamos en una figura cualquiera validamos "ver"
                if (f.contains((int) x, (int) y)) {
                    ver = true;
                }

                //si el toque esta dentro de una figura y esta coincide con la figura buscada
                // este girando o no, validamos "ver2"
                //
                if ((f.contains((int) x, (int) y)
                        && f.getClass().getSimpleName().startsWith(figuraBuscada.getSimpleName()))) {

                    ver2 = true;

                    //si es la figura le ponemos puntuacion 1
                    puntuacionPulsacion = 1;

                    //si esta girando le ponemos puntuacion 5
                    if (f.getClass().getSimpleName().endsWith("Girando")) {
                        puntuacionPulsacion = 5;
                    }

                    //si es el color buscado multiplicamos por 4 la puntuación
                    if (colorBuscado == f.getColor()) {
                        puntuacionPulsacion *= 4;
                    }

                    //capturamos la figura para borrarla fuera del bucle
                    figura = f;

                    //actualizamos la puntuacion
                    puntuacion = puntuacion + puntuacionPulsacion;
                    actualizarPuntuacion();

                    //aumentamos la velocidad del juego disminuyendo el tiempo entre figuras
                    //y haciendo que bajen mas rápido
                    velocidad++;
                    tiempoEntreFiguras--;

                    /* para ver parametros cuando pulsamos la figura
                    Toast.makeText(getContext(), "Has pulsado una figura " + f.getClass().getSimpleName()
                            + ", " + f.getColor()
                            + ", " + figuraBuscada.getClass()
                            + ", " + puntuacionPulsacion, Toast.LENGTH_SHORT).show();
                    */

                    //salimos del bucle
                    break;
                }
            }

            //borramos la pantalla
            figurasPantalla.remove(figura);

            /** hacemos el sonido correspondiente
             *  si se ha tocado una figura:
             *      si es la que buscamos
             *          si la puntuacion es 20 suena playOkGirando
             *          si es otra suena playOk
             *      si no es la buscamos suena playError
             *  si no se toca ninguna figura suena playPulsarNada
             */
            if (ver) {
                if (ver2) {
                    if (puntuacionPulsacion == 20) {
                        sonido.playOkGirando();
                    } else {
                        sonido.playOk();
                    }
                } else {
                    sonido.playError();
                }
            } else {
                sonido.playPulsarNada();
            }

        }

        return false;
    }
}
