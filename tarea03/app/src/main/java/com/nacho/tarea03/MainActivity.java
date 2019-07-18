package com.nacho.tarea03;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.BatteryManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bEnviar;
    Button bFormulario;
    TextView tvPorcentaje;

    double porcentaje;

    AjustesBD baseDatos;

    FilaAjustes datos;

    Intent enviarSMS, enviarEmail;
     private final int PERMISO_SMS = 203;

    private boolean permisoConcedido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaramos las vistas
        tvPorcentaje = findViewById(R.id.tvPorcentaje);
        bFormulario = findViewById(R.id.bFormulario);
        bEnviar = findViewById(R.id.bEnviar);
        baseDatos=new AjustesBD(this);

        //ponemos el porcentaje de la bateria
        tvPorcentaje.setText(comprobarBateria()+"%");

        //creamos un nuevo objeto para que no falle al arrancar la aplicacion
        datos= new FilaAjustes("0","","","","","","","","");


        comprobarDatos();
        //si la bateria es menor que lo que hemos marcado, nos envia los SMSs y/o emails(segun hayamos marcado)
        if (comprobarDatos() && (Double.valueOf(comprobarBateria())<Double.valueOf(datos.getUmbral())) ){

          enviar("automatico");

        }

        //listener de enviar
        //enviamos en modo manual
        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviar("manual");
            }
        });

        //listener para abrir la activity de opciones
        bFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pantallaFormulario = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(pantallaFormulario);
            }
        });

        //si no tenemos permiso para enviar SMS, lo pedimos
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},PERMISO_SMS);
        }
        else {
           permisoConcedido = true;

        }




    }

    //la respuesta de pedir permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permisoConcedido = true;

        }


    }

    /**
     * calculamos la bateria
     * @return el % de bateria restante
     */
    public String comprobarBateria(){

        IntentFilter filtroEvento = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent estadoActual = registerReceiver(null,filtroEvento);

        int nivelActual = estadoActual.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        int nivelMaximo = estadoActual.getIntExtra(BatteryManager.EXTRA_SCALE,0);

        porcentaje = (nivelActual / (double)nivelMaximo)*100;

        return String.valueOf(porcentaje);
    }

    /**
     * verificamos los datos que hay en la base de datos
     * y los cargamos en datos
     * @return true si hay datos recuperados y false si no hay datos
     */
    public boolean comprobarDatos(){
        datos = baseDatos.devolverDatos();

        if (datos == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * envia los SMSs y/* mails
     * @param modo manual o automatico
     */
    public void enviar(String modo){

        if (comprobarDatos()) {

            if (datos.getEmail().equals("true")){
                enviarEmails(modo, datos.getDireccion1(), datos.getDireccion2(), datos.getDireccion3());
            } else if (datos.getSms().equals("true")){
                //capturamos los datos de los textview
                enviarSMS(modo,datos.getContacto1(),datos.getContacto2(),datos.getContacto3());
            }  else {
                Toast.makeText(this, "Primero configure las opciones", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * envia sms
     * @param modo automatico o manual
     * @param m1 movil1
     * @param m2 movil2
     * @param m3 movil3
     */
    public void enviarSMS(String modo, String m1, String m2, String m3){

        //partimos el textview y cogemos el numero
        String[] parte = m1.split(",");
        String[] parte2 = m2.split(",");
        String[] parte3 = m3.split(",");

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(parte[1], null, modo, null, null);
            Toast.makeText(getApplicationContext(), "SMS al movil "+parte[1]+" enviado de forma "+modo,Toast.LENGTH_LONG).show();
            smsManager.sendTextMessage(parte2[1], null, modo, null, null);
            Toast.makeText(getApplicationContext(), "SMS al movil "+parte2[1]+" enviado de forma "+modo,Toast.LENGTH_LONG).show();
            smsManager.sendTextMessage(parte3[1], null, modo, null, null);
            Toast.makeText(getApplicationContext(), "SMS al movil "+parte3[1]+" enviado de forma "+modo,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Fallo al enviar los SMS, por favor, inténtalo otra vez.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }

    /**
     * envia los mails
     * @param modo automatico o manual
     * @param e1 email1
     * @param e2 email2
     * @param e3 email3
     */
    public void enviarEmails(String modo, String e1, String e2, String e3){

        Intent intentEmail = new Intent(Intent.ACTION_SEND);
        intentEmail.setData(Uri.parse("mailto:"));
        intentEmail.setType("text/plain");

        String titulo="";
        if (modo.equals("manual")) {
            titulo="INFORMACION";
        }

        if (modo.equals("automatico")) {
            titulo="ALARMA";
        }

        //destinatario
        intentEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{e1,e2,e3});
        //asunto
        intentEmail.putExtra(Intent.EXTRA_SUBJECT,titulo);
        //cueropo
        intentEmail.putExtra(Intent.EXTRA_TEXT,"La bateria esta al "+ comprobarBateria()+"%");
        //enviamos a la aplicacion de correo
        startActivity(intentEmail);
    }
}
