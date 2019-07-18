package com.nacho.tarea04;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISO_ESCRITURA = 201;
    private static final int SOLICITUD_VIDEO = 202;
    private static final int SOLICITUD_CAMARA = 203;
    private Uri uriFoto;

    Button bHacerFoto,bGrabar;
    EditText etDuracionMaxima, etNombreFoto, etCarpeta;
    RadioButton rbCalidadBaja, rbCalidadAlta;

    int calidadVideo=0;
    int duracionMaxima=0;
    File archivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rbCalidadBaja = findViewById(R.id.rbCalidadBaja);
        rbCalidadAlta = findViewById(R.id.rbCalidadAlta);
        etDuracionMaxima = findViewById(R.id.etDuracionGrabacion);
        etNombreFoto = findViewById(R.id.etNombreFoto);
        etCarpeta = findViewById(R.id.etCarpetaFoto);
        bGrabar = findViewById(R.id.bGrabar);
        bHacerFoto = findViewById(R.id.bHacerFoto);




        //verificamos si tenemos permiso de antes
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISO_ESCRITURA);
        } else {
            //Desbloqueamos los botones, ya tenemos el permiso de antes
            bGrabar.setEnabled(true);
            bHacerFoto.setEnabled(true);
        }

        bGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitarGrabacionVideo();
            }
        });

        bHacerFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                solicitarFoto();

            }
        });
    }

    //si el resultado es que han dado los permisos
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Desbloqueamos los botones, hemos obtenido el permiso
            bGrabar.setEnabled(true);
            bHacerFoto.setEnabled(true);
        }
    }

    /**
     * verificamos que tenemos camara
     * @return true si tiene camara y false si no
     */
    private boolean verificarCamara(){
        PackageManager manager = this.getPackageManager();
        return manager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    /**
     * si tiene camara hacemos el intent del video con las opciones dadas
     */
    private void solicitarGrabacionVideo(){
        if(verificarCamara()){
            if (rbCalidadBaja.isChecked()){
                calidadVideo=0;
            } else {
                calidadVideo=1;
            }
            duracionMaxima = Integer.valueOf(etDuracionMaxima.getText().toString());

            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,calidadVideo); //0 baja, 1 alta
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,duracionMaxima);
            startActivityForResult(intent,SOLICITUD_VIDEO);
        }
        else {
            //No tenemos camara en el dispositivo
            Toast.makeText(this, "No se ha detectado camara en el dispositivo", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * creamos un archivo temporal para que luego cree ahi la foto
     * @return nos devuielve el archivo temporal
     * @throws IOException
     */
    private File crearArchivoImagen()throws IOException{
        String nombreFichero="";
        File directorioAlmacenaje= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File temp=null;
        String rutaCarpetaRaiz = Environment.getExternalStorageDirectory().getPath();

        //para elegir el nombre de al foto
        if (etNombreFoto.getText().toString().equals("")){
            String tiempo=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            nombreFichero="JPEG_"+tiempo+"_";
        } else {
            nombreFichero=etNombreFoto.getText().toString();
        }

        //para elegir la carpeta
        //si no existe cogemos por defecto la carpeta de las imagenes /sdcard/pictures
        if (etCarpeta.getText().toString().equals("")) {

            directorioAlmacenaje= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //si no, buscamos la carpeta en /sdcard y si no existe la creamos
        } else {

            File carpetaNueva = new File("/sdcard/"+etCarpeta.getText().toString());

            if (!carpetaNueva.exists()){

                carpetaNueva.mkdir();
            }

            directorioAlmacenaje=carpetaNueva.getAbsoluteFile();


        }

        return File.createTempFile(nombreFichero,".jpg",directorioAlmacenaje);

    }

    /**
     * si tiene camara hacemos el intent de la foto con las opciones dadas
     */
    private void solicitarFoto(){
        if(verificarCamara()){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                archivo = crearArchivoImagen();
                uriFoto = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        archivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uriFoto);
                startActivityForResult(intent,SOLICITUD_CAMARA);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            //No tenemos camara en el dispositivo
            Toast.makeText(this, "No se ha detectado camara en el dispositivo", Toast.LENGTH_SHORT).show();


        }
    }

    /**
     * añadimos la foto a la galeria
     */
    public void añadirAGaleria(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uriFoto);
        this.sendBroadcast(mediaScanIntent);
    }

    //verificamos que se han hecho bien el video o la foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == SOLICITUD_VIDEO && resultCode == RESULT_OK){
           Toast.makeText(this, "Video grabado correctamente", Toast.LENGTH_SHORT).show();
       }
       else   if(requestCode == SOLICITUD_CAMARA && resultCode == RESULT_OK){
           Toast.makeText(MainActivity.this,
                   "Foto guardada correctamente en "+archivo.getAbsolutePath(), Toast.LENGTH_LONG).show();
            //la añadimos a la galeria
           añadirAGaleria();
       }
    }
}



