package com.nacho.tarea08;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Cuadrado {

    private int ancho;
    private int alto;
    private int x;
    private int y;
    private Rect rect;
    private Paint paint;
    private int color;



    public Cuadrado(int color, int ancho, int alto, int x, int y) {

        paint = new Paint();
        paint.setColor(color);

        this.ancho = ancho;
        this.color = color;
        this.alto = alto;
        this.x = x;
        this.y =  y;
        rect = new Rect(x,y,x+ancho,y+alto);
    }

    public boolean intersect(Cuadrado x){

        return Rect.intersects(rect,x.rect);
        //con este no se ve el rojo
        // return rect.intersect(x.rect);

    }


    public void renderizar(Canvas canvas) {
        canvas.drawRect(this.rect,paint);
    }

    public Rect getRect() {
        return rect;
    }

    public void subir(int px) {
        rect.top -= px;
        rect.bottom -= px;
    }

    public void desplazarHorizontal(int px) {
        rect.left += px;
        rect.right += px;
    }

    public int getColor() {
        return color;
    }
}
