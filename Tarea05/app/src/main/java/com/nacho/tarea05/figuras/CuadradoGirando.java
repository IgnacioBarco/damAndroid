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




public class CuadradoGirando implements Figura, ValueAnimator.AnimatorUpdateListener {

    private int left, top, right, bottom;
    private int anguloActual;
    private int[] colores = {Color.WHITE, Color.BLUE, Color.RED, Color.YELLOW};
    private Rect rect;
    private ValueAnimator animator;
    private Handler handler;
    private Paint paint;

    public CuadradoGirando(Paint p, int left, int right, int top, int bottom) {

        this.rect = new Rect(left, top, right, bottom);

        paint = new Paint();
        this.paint=p;

        //configuramos la animacion
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
        //si no ha empezado la animacion, que empiece
        if(!animator.isStarted()){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    animator.start();
                }
            });
        }
        //operamos con el canvas
        canvas.save();
        canvas.rotate(anguloActual,rect.centerX(),rect.centerY());
        canvas.drawRect(rect,paint);
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

    public boolean contains(int x,int y){
        return rect.contains(x,y);
    }

    @Override
    public int getColor() {
        return paint.getColor();
    }

    public void setColor(int color){
        paint.setColor(color);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        anguloActual = (int) valueAnimator.getAnimatedValue();
    }
}

