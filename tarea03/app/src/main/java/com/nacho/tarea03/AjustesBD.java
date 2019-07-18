package com.nacho.tarea03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nacho on 20/03/2018.
 */

public class AjustesBD extends SQLiteOpenHelper {

    static final String NOMBRE_BD = "ajustes.bd";
    static final int VERSION = 1;
    String res = "";
    Cursor cursor;

    public AjustesBD(Context context) {
        super(context, NOMBRE_BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ajustes (" +
                "umbral"     + " VARCHAR NOT NULL, " +
                "direccion1" + " VARCHAR , " +
                "direccion2" + " VARCHAR , " +
                "direccion3" + " VARCHAR , " +
                "contacto1"  + " VARCHAR , " +
                "contacto2"  + " VARCHAR , " +
                "contacto3"  + " VARCHAR , " +
                "sms"        + " VARCHAR NOT NULL, " +
                "email"      + " VARCHAR NOT NULL );");

    }

    /*
        metodo por si la version no es la actual
        borra la bd y crea una nueva
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int anteriorVersion, int nuevaVersion) {
        db.execSQL("DROP TABLE ajustes");
        onCreate(db);
    }


    public String insertarDatos(FilaAjustes datos) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valor = new ContentValues();
        valor.put("umbral", datos.getUmbral());
        valor.put("direccion1", datos.getDireccion1());
        valor.put("direccion2", datos.getDireccion2());
        valor.put("direccion3", datos.getDireccion3());
        valor.put("contacto1", datos.getContacto1());
        valor.put("contacto2", datos.getContacto2());
        valor.put("contacto3", datos.getContacto3());
        valor.put("sms", datos.getSms());
        valor.put("email", datos.getEmail());

        //verificamos que ha recuperado los datos
        long resultado = db.insert("ajustes", null, valor);
        if (resultado == -1) {
            return "Fallo al guardar los datos";
        } else {
            return "Datos guardados";
        }

    }

    /*
        Para actualizar los datos
        aunque no la llegamos a utilizar
     */
     public String actualizarDatos(int id,FilaAjustes datos) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valor = new ContentValues();
        valor.put("umbral", datos.getUmbral());
        valor.put("direccion1", datos.getDireccion1());
        valor.put("direccion2", datos.getDireccion2());
        valor.put("direccion3", datos.getDireccion3());
        valor.put("contacto1", datos.getContacto1());
        valor.put("contacto2", datos.getContacto2());
        valor.put("contacto3", datos.getContacto3());
        valor.put("sms", datos.getSms());
        valor.put("email", datos.getEmail());
        int resultado = db.update("ajustes", valor, "ID = ?", new String[]{String.valueOf(id)});
        if (resultado == 1) {
            return "Actualización correcta";
        } else {
            return "Ha fallado la actualización: " + resultado;
        }


    }

    /**
     * Recupera los datos de la tabla
     * @return devolvemos un objeto con todos los valores de la tabla
     */
    public FilaAjustes devolverDatos() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ajustes",null);

        if(cursor.moveToNext()){
            FilaAjustes datos = new FilaAjustes(
                    cursor.getString(0),  // umbral
                    cursor.getString(1),  // direccion1
                    cursor.getString(2),  // direccion2
                    cursor.getString(3),  // direccion3
                    cursor.getString(4),  // contacto1
                    cursor.getString(5),  // contacto2
                    cursor.getString(6),  // contacto3
                    cursor.getString(7),  // sms
                    cursor.getString(8)); // email

        return datos;
        }
        else {
            return null;
        }
    }

    /**
     * borra todos los datos de la tabla
     * @return devuelve si ha borrado o ha fallado
     */
    public String borrarDatos() {
        res = "";

        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.delete("ajustes", null,null);
        if (resultado == 1) {
            return "Datos borrados";
        } else {
            return "Ha fallado el borrado";
        }

    }


}
