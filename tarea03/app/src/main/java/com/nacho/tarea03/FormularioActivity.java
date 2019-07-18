package com.nacho.tarea03;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nacho on 20/03/2018.
 */

public class FormularioActivity extends AppCompatActivity {

    final int SOLICITUD_CONTACTO1 = 301;
    final int SOLICITUD_CONTACTO2 = 302;
    final int SOLICITUD_CONTACTO3 = 303;

    TextView tvPorcentaje;
    TextView tvContactoSMS1, tvContactoSMS2, tvContactoSMS3;

    SeekBar sbPorcentaje;

    EditText etEmail1;
    EditText etEmail2;
    EditText etEmail3;

    Button bContactos;
    Button bContactos2;
    Button bContactos3;
    Button bGuardar;

    CheckBox cbEnviarSMS;
    CheckBox cbEnviarEmail;

    AjustesBD baseDatos;

    Intent pantallaInicio;

    FilaAjustes filaAjustes;

    String contacto1, contacto2, contacto3;
    String tlf1, tlf2, tlf3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        //declaramos las vistas
        baseDatos = new AjustesBD(this);
        tvContactoSMS1 = findViewById(R.id.tvContactoSMS1);
        tvContactoSMS2 = findViewById(R.id.tvContactoSMS2);
        tvContactoSMS3 = findViewById(R.id.tvContactoSMS3);
        bGuardar = findViewById(R.id.bGuardar);
        bContactos = findViewById(R.id.bContactos);
        bContactos2 = findViewById(R.id.bContactos2);
        bContactos3 = findViewById(R.id.bContactos3);
        tvPorcentaje = findViewById(R.id.tvPorcentaje);
        sbPorcentaje = findViewById(R.id.sbPorcentaje);
        etEmail1 = findViewById(R.id.etEmail1);
        etEmail2 = findViewById(R.id.etEmail2);
        etEmail3 = findViewById(R.id.etEmail3);
        cbEnviarSMS = findViewById(R.id.cbEnviarSMS);
        cbEnviarEmail = findViewById(R.id.cbEnviarEmail);

        //generamos un objeto inicial por si no hay opciones
        //para que no falle
        filaAjustes = new FilaAjustes("0","","","","","","","","");

        //ponemos el porcentaje de la bateria con numero
        tvPorcentaje.setText("" + sbPorcentaje.getProgress() + "%");

        //listener para que nos cambie el valor de seekbar en las opciones
        sbPorcentaje.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvPorcentaje.setText("" + sbPorcentaje.getProgress() + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tvPorcentaje.setText("" + sbPorcentaje.getProgress() + "%");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvPorcentaje.setText("" + sbPorcentaje.getProgress() + "%");
            }
        });

        //recuperamos los datos y pintamos los valores gusrdados enla base de datos
        pintarDatos();


        //listener para el boton de contactos
        bContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                elegirContactos(SOLICITUD_CONTACTO1);

            }
        });

        //listener para el boton de contactos
        bContactos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                elegirContactos(SOLICITUD_CONTACTO2);

            }
        });

        //listener para el boton de contactos
        bContactos3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                elegirContactos(SOLICITUD_CONTACTO3);

            }
        });

        //listener para el boton de guardar
        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (guardarDatos()){
                    finish();
                }

            }
        });

    }

    /**
     * recuperamos los datos de la base de datos
     * y los pintamos
     */
    public void pintarDatos() {

        Cursor cursor = null;
        filaAjustes = baseDatos.devolverDatos();

        if (filaAjustes == null) {
            //No hay datos que recuperar
        } else {
            sbPorcentaje.setProgress(Integer.valueOf(filaAjustes.getUmbral()));
            etEmail1.setText(filaAjustes.getDireccion1());
            etEmail2.setText(filaAjustes.getDireccion2());
            etEmail3.setText(filaAjustes.getDireccion3());
            tvContactoSMS1.setText(filaAjustes.getContacto1());
            tvContactoSMS2.setText(filaAjustes.getContacto2());
            tvContactoSMS3.setText(filaAjustes.getContacto3());
            if (filaAjustes.getSms().equals("true")) {
                cbEnviarSMS.setChecked(true);
            } else {
                cbEnviarSMS.setChecked(false);
            }
            if (filaAjustes.getEmail().equals("true")) {
                cbEnviarEmail.setChecked(true);
            } else {
                cbEnviarEmail.setChecked(false);
            }
        }


    }

    //para que abra la lista de contactos
    public void elegirContactos(int SOLICITUD_CONTACTO) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, SOLICITUD_CONTACTO);

    }

    //cuando elegimos un contacto, que sepa que hacer con el
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //que verifique que es uno de los codigos y que el usuario ha elegido una opcion
        if ((requestCode == SOLICITUD_CONTACTO1 || requestCode == SOLICITUD_CONTACTO2 || requestCode == SOLICITUD_CONTACTO3) && resultCode == RESULT_OK) {
            Uri contacto = data.getData();
            Cursor cursor = getContentResolver().query(contacto, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.Data.DISPLAY_NAME}, null, null, null);

            if (cursor != null && cursor.moveToNext()) {
                int indiceColTelefono = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String numero = cursor.getString(indiceColTelefono);

                int indiceColNombre = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);
                String nombre = cursor.getString(indiceColNombre);

                if (requestCode == SOLICITUD_CONTACTO1) {
                    contacto1 = nombre;
                    tlf1 = numero;
                    tvContactoSMS1.setText(nombre+","+numero);
                }
                if (requestCode == SOLICITUD_CONTACTO2) {
                    contacto2 = nombre;
                    tlf2 = numero;
                    tvContactoSMS2.setText(nombre+","+numero);
                }
                if (requestCode == SOLICITUD_CONTACTO3) {
                    contacto3 = nombre;
                    tlf3 = numero;
                    tvContactoSMS3.setText(nombre+","+numero);
                }

                Toast.makeText(this, numero + ", " + nombre, Toast.LENGTH_SHORT).show();
            }

            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * verifica que hay datos en todos los campos
     * y los guarda los datos en la base de datos
     * @return devuelve true si los gusrda y false si no
     */
    public boolean guardarDatos() {

        boolean verificacion = true;
        String res = "";

        //if (cbEnviarEmail.isChecked()) {
            if (etEmail1.getText().toString().equals("")) {
                verificacion = false;
                res = res + "Falta poner la direccion1 \n";
            }

            if (etEmail2.getText().toString().equals("")) {
                verificacion = false;
                res = res + "Falta poner la direccion2 \n";
            }

            if (etEmail3.getText().toString().equals("")) {
                verificacion = false;
                res = res + "Falta poner la direccion3 \n";
            }
        //}

        //if (cbEnviarSMS.isChecked()) {
            if (tvContactoSMS1.getText().toString().equals("")) {
                verificacion = false;
                res = res + "Falta poner el contacto1 \n";
            }

            if (tvContactoSMS2.getText().toString().equals("")) {
                verificacion = false;
                res = res + "Falta poner el contacto2 \n";
            }

            if (tvContactoSMS3.getText().toString().equals("")) {
                verificacion = false;
                res = res + "Falta poner el contacto3 \n";
            }

        //}

        if (verificacion) {

            //Toast.makeText(this, "verificacion ok", Toast.LENGTH_SHORT).show();

            FilaAjustes verificarVacio = baseDatos.devolverDatos();

            if (verificarVacio != null) {
                //Toast.makeText(this, "borramos datos", Toast.LENGTH_SHORT).show();
                System.out.println(baseDatos.borrarDatos());
            }

            FilaAjustes filaAjustes = new FilaAjustes(
            String.valueOf(sbPorcentaje.getProgress()),
            etEmail1.getText().toString(),
            etEmail2.getText().toString(),
            etEmail3.getText().toString(),
            tvContactoSMS1.getText().toString(),
            tvContactoSMS2.getText().toString(),
            tvContactoSMS3.getText().toString(),
            String.valueOf(cbEnviarSMS.isChecked()),
            String.valueOf(cbEnviarEmail.isChecked()));

            Toast.makeText(this, baseDatos.insertarDatos(filaAjustes), Toast.LENGTH_SHORT).show();
            return true;

        } else {
            Toast.makeText(this, "No se han guardado los datos, pues faltan campos por rellenar: \n"+res, Toast.LENGTH_SHORT).show();
            return false;
        }

    }


}
