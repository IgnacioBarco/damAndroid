package com.nacho.tarea05.figuras;

import android.graphics.Canvas;

public interface Figura {
    /**
     * pintamos la figura
     * @param c le pasamos el canvas donde tiene que pintarse
     */
    void renderizar(Canvas c);

    /**
     * Comprobamos si esta dentro de la pantalla
     * @param alto le pasamos la posicion donde esta
     * @return devuelve true si esta en la pantalla y false si esta fuera
     */
    boolean isFueraPantalla(int alto);

    /**
     * desplaza la figura hacia abajo
     * @param pixelesDescenso cantidad de pixeles que baja
     */
    void descender(int pixelesDescenso);

    /**
     * comprobamos si la posicion que le enviamos esta dentro de la figura pintada
     * @param x
     * @param y
     * @return true si esta dentro y false si no
     */
    boolean contains(int x, int y);

    /**
     * comprobamos que color tiene
     * @return devolvemos el codigo int de la figura
     */
    int getColor();
}
