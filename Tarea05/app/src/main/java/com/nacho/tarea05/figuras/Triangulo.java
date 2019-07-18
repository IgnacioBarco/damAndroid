package com.nacho.tarea05.figuras;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;


public class Triangulo implements Figura {

    private final Paint pincel;
    private Rect rect;

    private Point v1;
    private Point v2;
    private Point v3;
    private Path caminoPuntos;

    public Triangulo(Paint p, Point v1,Point v2,Point v3) {
       this.rect = new Rect();
       this.pincel = p;
       this.v1 = v1;
       this.v2 = v2;
       this.v3 = v3;
       caminoPuntos = new Path();

    }

    @Override
    public void renderizar(Canvas canvas) {
        //pintamos el triangulo uniendo puntos
        caminoPuntos.reset();

        caminoPuntos.moveTo(v1.x,v1.y);
        caminoPuntos.lineTo(v2.x,v2.y);
        caminoPuntos.lineTo(v3.x,v3.y);
        caminoPuntos.moveTo(v3.x,v3.y);
        caminoPuntos.lineTo(v2.x,v2.y);

        int right = v3.x;
        int left = v2.x;
        int top = v1.y;
        int bottom = v3.y;
        rect.set(left,top,right,bottom);

        canvas.drawPath(caminoPuntos,pincel);

        /* para ver la zona donde pulsamos
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect,pincel);
        pincel.setStyle(Paint.Style.FILL);
        */
    }

    @Override
    public boolean isFueraPantalla(int alto) {
        return rect.top > alto;
    }

    @Override
    public void descender(int pixeles) {
        //descendemos la zona de pulsacion
        int top = rect.top + pixeles;
        int bottom = rect.bottom + pixeles;
        rect.set(rect.left,top,rect.right,bottom);

        //descendemos el triangulo
        v1.y += pixeles;
        v2.y += pixeles;
        v3.y += pixeles;
    }

    public void setColor(int color){
        this.pincel.setColor(color);
    }


    public boolean contains(int x,int y){
        //System.out.println("left: "+rect.left+", right: "+ rect.right+", x: "+x);
        //System.out.println("top: "+rect.top+", bottom: "+ rect.bottom+", y: "+y);
        return rect.contains(x,y);
    }

    @Override
    public int getColor() {
        return pincel.getColor();
    }
}
