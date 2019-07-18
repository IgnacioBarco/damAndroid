package com.nacho.test1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {

    private Button botonAct2;
    private int contador;
    private TextView contador1;
    private Button botonLista;
    private Button botonAsync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contador1= findViewById(R.id.contador1);
        botonAct2 = findViewById(R.id.botonAct2);
        botonLista = findViewById(R.id.botonList);
        botonAsync = findViewById(R.id.botonAsync);

        contador = 0;
        contador1.setText(""+contador);

        botonAct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Activity2.class);
                contador++;
                intent.putExtra("contador",contador);

                startActivityForResult(intent,100);
            }
        });

        botonLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentLista = new Intent(MainActivity.this,ActivityLista.class);

                startActivityForResult(intentLista,200);
            }
        });

        botonAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, ActivityAsync.class);
                intent3.putExtra("datoEnviado","hola");
                startActivityForResult(intent3, 300);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==100 && resultCode==RESULT_OK){

            contador=data.getIntExtra("contador",-1);
            contador1.setText(""+contador);


        }

        if (requestCode==200 && resultCode==RESULT_OK){

            Toast.makeText(this, "Habian pulsado la lista "+data.getStringExtra("dato"), Toast.LENGTH_SHORT).show();
        }

        if (requestCode==300 && resultCode==RESULT_OK){
            Toast.makeText(this, "Te devuelve: "+data.getStringExtra("resultado"), Toast.LENGTH_SHORT).show();


            int notifId=1; //Identificador de la notificación, para futuras modificaciones.
            NotificationCompat.Builder constructorNotif = new NotificationCompat.Builder(this);
            constructorNotif.setSmallIcon(R.drawable.ic_launcher_background);
            constructorNotif.setContentTitle("Mi notificación");
            constructorNotif.setContentText("Has recibido una notificación!!");

            Intent resultadoIntent = new Intent(this, MainActivity.class);
            //El objeto stackBuilder crea un back stack que nos asegura que el botón de "Atrás" del
            //dispositivo nos lleva desde la Actividad a la pantalla principal
            TaskStackBuilder pila = TaskStackBuilder.create(this);
            // El padre del stack será la actividad a crear
            pila.addParentStack(MainActivity.class);
            // Añade el Intent que comienza la Actividad al inicio de la pila
            pila.addNextIntent(resultadoIntent);
            PendingIntent resultadoPendingIntent = pila.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            constructorNotif.setContentIntent(resultadoPendingIntent);

            NotificationManager notificador =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificador.notify(notifId, constructorNotif.build());

        }

    }
}
