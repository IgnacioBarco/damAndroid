package com.nacho.tarea05.figuras;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;


public class TrianguloGirando implements Figura, ValueAnimator.AnimatorUpdateListener {

    private final Paint pincel;
    private Rect rect;

    private Point v1;
    private Point v2;
    private Point v3;
    private Path caminoPuntos;

    private ValueAnimator animator;
    private Handler handler;
    private int anguloActual;

    public TrianguloGirando(Paint p, Point v1,Point v2,Point v3) {
        this.rect = new Rect();
        this.pincel = p;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        caminoPuntos = new Path();

        animator = ValueAnimator.ofInt(0,360);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(3000);
        animator.addUpdateListener(this);
        this.handler = new Handler(Looper.getMainLooper());
        anguloActual=0;
    }

    @Override
    public void renderizar(Canvas canvas) {
        if(!animator.isStarted()){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    animator.start();
                }
            });
        }
        canvas.save();
        canvas.rotate(anguloActual,rect.centerX(),rect.centerY());

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

        canvas.restore();

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
        int top = rect.top + pixeles;
        int bottom = rect.bottom + pixeles;
        rect.set(rect.left,top,rect.right,bottom);

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

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        anguloActual = (int) valueAnimator.getAnimatedValue();
    }
}
