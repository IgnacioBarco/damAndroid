package com.nacho.tarea05.figuras;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class GenerarFiguraAleatoria {



    private DisplayMetrics medidas;
    private Paint paint;
    private int aleatorio, aleatorioColor;
    private int[] colores = {Color.WHITE, Color.BLUE, Color.RED};


    /**
     * genera un color aleatorio
     * desde 0 a n-1
     * @return devuelve un color aleatorio de colores[]
     */
    public int generarPincel(){
        aleatorioColor=(int)(Math.random()*3);
        return colores[aleatorioColor];
    }


    public GenerarFiguraAleatoria(DisplayMetrics medidas){
        //las medidas es el tamaño de la pantalla
        this.medidas=medidas;
        aleatorio=0;
        paint = new Paint();
        paint.setColor(generarPincel());
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    public Cuadrado generarCuadrado(){
       /*medidas con pixeles fijos
        int left = (medidas.widthPixels/2)-100;
        int right = (medidas.widthPixels/2)+100;
        int top = -100;
        int bottom = 100;
       */

        int left = (medidas.widthPixels/2)-(medidas.widthPixels/8);
        int right = (medidas.widthPixels/2)+(medidas.widthPixels/8);
        int top = -(medidas.widthPixels/8);
        int bottom = (medidas.widthPixels/8);

        paint.setColor(generarPincel());

        return new Cuadrado(paint,left, right, top, bottom);
    }


    public CuadradoGirando generarCuadradoGirando(){
        int left = (medidas.widthPixels/2)-(medidas.widthPixels/8);
        int right = (medidas.widthPixels/2)+(medidas.widthPixels/8);
        int top = -(medidas.widthPixels/8);
        int bottom = (medidas.widthPixels/8);

        paint.setColor(generarPincel());

        return new CuadradoGirando(paint,left, right, top, bottom);
    }


    public Rectangulo generarRectangulo(){
        //150 pixeles originalmente
        int left = (medidas.widthPixels/2)-(medidas.widthPixels/5);
        int right = (medidas.widthPixels/2)+(medidas.widthPixels/5);
        int top = -(medidas.widthPixels/8);
        int bottom = (medidas.widthPixels/8);

        paint.setColor(generarPincel());

        return new Rectangulo(paint,left, right, top, bottom);
    }


    public RectanguloGirando generarRectanguloGirando(){
        int left = (medidas.widthPixels/2)-(medidas.widthPixels/5);
        int right = (medidas.widthPixels/2)+(medidas.widthPixels/5);
        int top = -(medidas.widthPixels/8);
        int bottom = (medidas.widthPixels/8);

        paint.setColor(generarPincel());

        return new RectanguloGirando(paint,left, right, top, bottom);
    }


    public Triangulo generaTriangulo(){
        Point v1 = new Point();
        v1.y = -(medidas.widthPixels/5);
        v1.x = medidas.widthPixels/2;

        Point v2 = new Point();
        v2.y = 0;
        v2.x = (medidas.widthPixels/2)-(medidas.widthPixels/7);

        Point v3 = new Point();
        v3.y = 0;
        v3.x = (medidas.widthPixels/2)+(medidas.widthPixels/7);

        paint.setColor(generarPincel());

        return new Triangulo(paint,v1,v2,v3);
    }

    public TrianguloGirando generaTrianguloGirando(){
        Point v1 = new Point();
        v1.y = -(medidas.widthPixels/5);
        v1.x = medidas.widthPixels/2;

        Point v2 = new Point();
        v2.y = 0;
        v2.x = (medidas.widthPixels/2)-(medidas.widthPixels/7);

        Point v3 = new Point();
        v3.y = 0;
        v3.x = (medidas.widthPixels/2)+(medidas.widthPixels/7);

        paint.setColor(generarPincel());

        return new TrianguloGirando(paint,v1,v2,v3);


    }


    /**
     * generamos una figura aleatoria
     * @return devuelve la figura nueva
     */
    public Figura generarFigura(){
        //entre 1 y 6 excluido el 6
        aleatorio=(int) (Math.random()*7+1);

        switch (aleatorio){
            case 1:
                return generarRectangulo();

            case 2:
                return generarRectanguloGirando();

            case 3:
                return generarCuadrado();

            case 4:
                return generarCuadradoGirando();

            case 5:
                return generaTriangulo();

            case 6:
                return generaTrianguloGirando();

            default:
                return generaTrianguloGirando();
        }


    }
}
