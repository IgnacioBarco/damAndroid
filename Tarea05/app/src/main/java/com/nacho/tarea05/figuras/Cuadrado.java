package com.nacho.tarea05.figuras;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.DisplayMetrics;


public class Cuadrado implements Figura {

    private final Paint pincel;
    private Rect rect;
    private int left, top, right, bottom;


    public Cuadrado(Paint p, int left, int right, int top, int bottom) {
        this.rect = new Rect(left, top, right, bottom);
        this.pincel = p;


    }

    @Override
    public void renderizar(Canvas canvas) {

        canvas.drawRect(rect,pincel);

    }

    @Override
    public boolean isFueraPantalla(int alto) {
        return rect.top > alto;
    }

    @Override
    public void descender(int pixeles) {
        int top = rect.top + pixeles;
        int bottom = rect.bottom + pixeles;
        rect.set(rect.left,top,rect.right,bottom);
    }

    public void setColor(int color){
        this.pincel.setColor(color);
    }

    public boolean contains(int x,int y){
        return rect.contains(x,y);
    }

    @Override
    public int getColor() {
        return pincel.getColor();
    }
}
