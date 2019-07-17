package com.nacho.listview;

public class Contacto {

    private int id;
    private String nombre;
    private String telefono;
    private int imagen;

    public Contacto(int id, String nombre, String telefono, int imagen) {
        this.nombre = nombre;
        this.id = id;
        this.telefono = telefono;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Contacto) {
            Contacto c = (Contacto) obj;
            return c.getId() == this.getId();
        }
        return false;
    }
}
