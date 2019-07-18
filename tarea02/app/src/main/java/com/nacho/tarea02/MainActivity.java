package com.nacho.tarea02;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogoJugadores.RespuestaDialogoJugadores {

    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;

    ImageButton imageButton4;
    ImageButton imageButton5;
    ImageButton imageButton6;

    ImageButton imageButton7;
    ImageButton imageButton8;
    ImageButton imageButton9;

    TextView jugadorActual;

    TableLayout tablero;

    int numJugador = 1;

    int jugadores = 0;

    Juego juego;

    ImageButton botonClickado;

    String coodenadas;

    int cont = 0;

    /**
     * El listener que dice que hacer cuando pulsamos un boton
     */
    View.OnClickListener listenerBotones = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            botonClickado = (ImageButton) view;

            if (jugadores == 2) {
                switch (view.getId()) {
                    case R.id.imageButton1:
                        pulsaTecla(0, 0);
                        break;

                    case R.id.imageButton2:
                        pulsaTecla(0, 1);
                        break;

                    case R.id.imageButton3:
                        pulsaTecla(0, 2);
                        break;

                    case R.id.imageButton4:
                        pulsaTecla(1, 0);
                        break;

                    case R.id.imageButton5:
                        pulsaTecla(1, 1);
                        break;

                    case R.id.imageButton6:
                        pulsaTecla(1, 2);
                        break;

                    case R.id.imageButton7:
                        pulsaTecla(2, 0);
                        break;

                    case R.id.imageButton8:
                        pulsaTecla(2, 1);
                        break;

                    case R.id.imageButton9:
                        pulsaTecla(2, 2);
                        break;

                }

                juego.pintarTablero();

                cont++;

                if (juego.comprobarGanador()) {
                    Toast.makeText(MainActivity.this, "Hay un ganador", Toast.LENGTH_SHORT).show();
                    if (numJugador == 1) {
                        jugadorActual.setText("Ha ganado el jugador 2!!!");
                    } else {
                        jugadorActual.setText("Ha ganado el jugador 1!!!");
                    }
                    deshabilitarBotones();
                } else {
                    if (cont == 9) {
                        Toast.makeText(MainActivity.this, "Empate!!!", Toast.LENGTH_SHORT).show();
                        jugadorActual.setText("Empate!!!");
                    }
                }

                view.setEnabled(false);

            } else {

                numJugador = 1;
                jugadorActual.setText("Es el turno del jugador 1");


                switch (view.getId()) {
                    case R.id.imageButton1:
                        pulsaTecla(0, 0);
                        break;

                    case R.id.imageButton2:
                        pulsaTecla(0, 1);
                        break;

                    case R.id.imageButton3:
                        pulsaTecla(0, 2);
                        break;

                    case R.id.imageButton4:
                        pulsaTecla(1, 0);
                        break;

                    case R.id.imageButton5:
                        pulsaTecla(1, 1);
                        break;

                    case R.id.imageButton6:
                        pulsaTecla(1, 2);
                        break;

                    case R.id.imageButton7:
                        pulsaTecla(2, 0);
                        break;

                    case R.id.imageButton8:
                        pulsaTecla(2, 1);
                        break;

                    case R.id.imageButton9:
                        pulsaTecla(2, 2);
                        break;

                }


                juego.pintarTablero();
                cont++;


                if (juego.comprobarGanador()) {
                    Toast.makeText(MainActivity.this, "Hay un ganador", Toast.LENGTH_SHORT).show();
                    jugadorActual.setText("Ha ganado el jugador 1!!!");
                    deshabilitarBotones();

                } else if (cont == 9) {
                    Toast.makeText(MainActivity.this, "Empate!!!", Toast.LENGTH_SHORT).show();
                    jugadorActual.setText("Empate!!!");
                    System.out.println("empate");

                } else {
                    jugadorActual.setText("Es el turno de la maquina");
                    juego.pintarAleatorio();
                    juego.pintarNumero(numJugador, juego.getA(), juego.getB());
                    Toast.makeText(MainActivity.this, "Pensando...", Toast.LENGTH_SHORT).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            if (juego.getA() == 0 && juego.getB() == 0) {
                                imageButton1.setImageResource(R.drawable.ic_close_black_24dp);
                                imageButton1.setEnabled(false);
                            }
                            if (juego.getA() == 0 && juego.getB() == 1) {
                                imageButton2.setImageResource(R.drawable.ic_close_black_24dp);
                                imageButton2.setEnabled(false);
                            }
                            if (juego.getA() == 0 && juego.getB() == 2) {
                                imageButton3.setImageResource(R.drawable.ic_close_black_24dp);
                                imageButton3.setEnabled(false);
                            }
                            if (juego.getA() == 1 && juego.getB() == 0) {
                                imageButton4.setImageResource(R.drawable.ic_close_black_24dp);
                                imageButton4.setEnabled(false);
                            }
                            if (juego.getA() == 1 && juego.getB() == 1) {
                                imageButton5.setImageResource(R.drawable.ic_close_black_24dp);
                                imageButton5.setEnabled(false);
                            }
                            if (juego.getA() == 1 && juego.getB() == 2) {
                                imageButton6.setImageResource(R.drawable.ic_close_black_24dp);
                                imageButton6.setEnabled(false);
                            }
                            if (juego.getA() == 2 && juego.getB() == 0) {
                                imageButton7.setImageResource(R.drawable.ic_close_black_24dp);
                                imageButton7.setEnabled(false);
                            }
                            if (juego.getA() == 2 && juego.getB() == 1) {
                                imageButton8.setImageResource(R.drawable.ic_close_black_24dp);
                                imageButton8.setEnabled(false);
                            }
                            if (juego.getA() == 2 && juego.getB() == 2) {
                                imageButton9.setImageResource(R.drawable.ic_close_black_24dp);
                                imageButton9.setEnabled(false);
                            }

                            cont++;


                            if (juego.comprobarGanador()) {
                                jugadorActual.setText("Ha ganado la máquina!!!");
                                deshabilitarBotones();

                            } else if (cont == 9) {
                                Toast.makeText(MainActivity.this, "Empate!!!", Toast.LENGTH_SHORT).show();
                                jugadorActual.setText("Empate!!!");
                                System.out.println("empate");

                            } else {
                                jugadorActual.setText("Es el turno del jugador 1");
                            }


                        }
                    }, 2000);


                }


            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jugadorActual = findViewById(R.id.tvJugadorActual);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);

        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);

        imageButton7 = findViewById(R.id.imageButton7);
        imageButton8 = findViewById(R.id.imageButton8);
        imageButton9 = findViewById(R.id.imageButton9);

        tablero = findViewById(R.id.tablero);
        imageButton1.setOnClickListener(listenerBotones);
        imageButton2.setOnClickListener(listenerBotones);
        imageButton3.setOnClickListener(listenerBotones);
        imageButton4.setOnClickListener(listenerBotones);
        imageButton5.setOnClickListener(listenerBotones);
        imageButton6.setOnClickListener(listenerBotones);
        imageButton7.setOnClickListener(listenerBotones);
        imageButton8.setOnClickListener(listenerBotones);
        imageButton9.setOnClickListener(listenerBotones);

        deshabilitarBotones();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuInstrucciones) {
            DialogoInstrucciones di = new DialogoInstrucciones();
            di.show(getFragmentManager(), "Instrucciones");
            return true;
        }

        if (id == R.id.menuConfigurar) {
            DialogoJugadores dj = new DialogoJugadores();
            dj.show(getFragmentManager(), "Mi diálogo");
            return true;
        }

        if (id == R.id.menuJugar) {
            if (jugadores != 0) {

                Toast.makeText(getApplicationContext(), "Comencemos!!!", Toast.LENGTH_LONG).show();

                if (jugadores == 1) {
                    numJugador = 1;
                }
                jugadorActual.setText("Es el turno del jugador " + numJugador);

                imageButton1.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                imageButton2.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                imageButton3.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                imageButton4.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                imageButton5.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                imageButton6.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                imageButton7.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                imageButton8.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                imageButton9.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);

                habilitarBotones();

                this.juego = new Juego();

                cont = 0;

            } else {
                Toast.makeText(this, "No se puede empezar el juego sin elegir el número de jugadores", Toast.LENGTH_SHORT).show();
            }

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRespuesta(int numJug) {
        jugadores = numJug;
        if (jugadores == 1) {
            Toast.makeText(this, "Has elegido jugar contra la maquina", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Has elegido jugar dos personas", Toast.LENGTH_SHORT).show();
        }


    }

    public void habilitarBotones() {
        imageButton1.setEnabled(true);
        imageButton2.setEnabled(true);
        imageButton3.setEnabled(true);
        imageButton4.setEnabled(true);
        imageButton5.setEnabled(true);
        imageButton6.setEnabled(true);
        imageButton7.setEnabled(true);
        imageButton8.setEnabled(true);
        imageButton9.setEnabled(true);
    }

    public void deshabilitarBotones() {
        imageButton1.setEnabled(false);
        imageButton2.setEnabled(false);
        imageButton3.setEnabled(false);
        imageButton4.setEnabled(false);
        imageButton5.setEnabled(false);
        imageButton6.setEnabled(false);
        imageButton7.setEnabled(false);
        imageButton8.setEnabled(false);
        imageButton9.setEnabled(false);
    }

    public void pulsaTecla(int a, int b) {
        if (numJugador == 1) {
            botonClickado.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
        } else botonClickado.setImageResource(R.drawable.ic_close_black_24dp);
        botonClickado.setEnabled(false);
        if (numJugador == 1) {
            juego.pintarNumero(numJugador, a, b);
            numJugador = 2;
        } else {
            juego.pintarNumero(numJugador, a, b);
            numJugador = 1;
        }
        jugadorActual.setText("Es el turno del jugador " + numJugador);
    }
}
