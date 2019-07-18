package com.nacho.test2;

import android.widget.ImageView;
import android.widget.TextView;

public class ObjetoFila {

    private int imagen1;
    private int imagen2;
    private String texto;

    public int getImagen1() {
        return imagen1;
    }

    public void setImagen1(int imagen1) {
        this.imagen1 = imagen1;
    }

    public int getImagen2() {
        return imagen2;
    }

    public void setImagen2(int imagen2) {
        this.imagen2 = imagen2;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ObjetoFila(int imagen1, int imagen2, String texto) {
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.texto = texto;
    }




}
