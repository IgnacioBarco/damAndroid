package com.nacho.tarea03;

/**
 * Created by Nacho on 24/03/2018.
 */


/*
    Clase creada para ser mas facil el intercambio de datos con la base de datos
 */
public class FilaAjustes {

    String umbral;
    String direccion1;
    String direccion2;
    String direccion3;
    String contacto1;
    String contacto2;
    String contacto3;
    String sms;
    String email;


    public FilaAjustes(String umbral, String direccion1, String direccion2, String direccion3, String contacto1, String contacto2, String contacto3, String sms, String email) {
        this.umbral = umbral;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.direccion3 = direccion3;
        this.contacto1 = contacto1;
        this.contacto2 = contacto2;
        this.contacto3 = contacto3;
        this.sms = sms;
        this.email = email;
    }

    public String getUmbral() {
        return umbral;
    }

    public void setUmbral(String umbral) {
        this.umbral = umbral;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getDireccion3() {
        return direccion3;
    }

    public void setDireccion3(String direccion3) {
        this.direccion3 = direccion3;
    }

    public String getContacto1() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getContacto2() {
        return contacto2;
    }

    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }

    public String getContacto3() {
        return contacto3;
    }

    public void setContacto3(String contacto3) {
        this.contacto3 = contacto3;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
