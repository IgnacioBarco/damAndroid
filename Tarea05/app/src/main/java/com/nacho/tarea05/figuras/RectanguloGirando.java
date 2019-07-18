package com.nacho.tarea05.figuras;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;


public class RectanguloGirando implements Figura, ValueAnimator.AnimatorUpdateListener {

    private final Paint pincel;
    private Rect rect;
    private int left, top, right, bottom;
    private int anguloActual;

    private ValueAnimator animator;
    private Handler handler;


    public RectanguloGirando(Paint p, int left, int right, int top, int bottom) {
        this.rect = new Rect(left, top, right, bottom);
        this.pincel = p;
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
        canvas.drawRect(rect,pincel);
       canvas.restore();
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

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        anguloActual = (int) valueAnimator.getAnimatedValue();
    }
}
