package com.nacho.tarea02;

/**
 * Created by Nacho on 19/03/2018.
 */

public class Juego {


    int[][] tablero;

    int i = 0;

    int j = 0;

    int a, b;


    public Juego() {


        tablero = new int[3][3];

        resetearTablero();

        pintarTablero();


    }


    public void resetearTablero() {

        tablero[0][0] = 0;

        tablero[0][1] = 0;

        tablero[0][2] = 0;


        tablero[1][0] = 0;

        tablero[1][1] = 0;

        tablero[1][2] = 0;


        tablero[2][0] = 0;

        tablero[2][1] = 0;

        tablero[2][2] = 0;

    }


    public void pintarTablero() {

        System.out.print("-------\n");

        for (i = 0; i <= 2; i++) {


            for (j = 0; j <= 2; j++) {

                System.out.print("|" + tablero[i][j]);

                if (j == 2) {

                    System.out.print("|\n-------\n");


                }

            }

        }

    }


    public void pintarNumero(int jugador, int posicion1, int posicion2) {


        if (jugador == 1) {

            tablero[posicion1][posicion2] = 1;

        } else {

            tablero[posicion1][posicion2] = 2;

        }

    }


    public boolean comprobarGanador() {

        boolean ver = false;

        if (tablero[0][0] == tablero[0][1] && tablero[0][0] == tablero[0][2] && tablero[0][0] != 0

                || tablero[1][0] == tablero[1][1] && tablero[1][0] == tablero[1][2] && tablero[1][0] != 0

                || tablero[2][0] == tablero[2][1] && tablero[2][0] == tablero[2][2] && tablero[2][0] != 0

                || tablero[0][0] == tablero[1][0] && tablero[0][0] == tablero[2][0] && tablero[0][0] != 0

                || tablero[0][1] == tablero[1][1] && tablero[0][1] == tablero[2][1] && tablero[0][1] != 0

                || tablero[0][2] == tablero[1][2] && tablero[0][2] == tablero[2][2] && tablero[0][2] != 0

                || tablero[0][0] == tablero[1][1] && tablero[0][0] == tablero[2][2] && tablero[0][0] != 0

                || tablero[2][0] == tablero[1][1] && tablero[2][0] == tablero[0][2] && tablero[2][0] != 0) {

            System.out.println("Hay un ganador");

            ver = true;

        } else {

            System.out.println("No hay un ganador");

        }

        return ver;


    }


    public String pintarAleatorio() {

        boolean ver = false;

        do {

            a = (int) Math.floor(Math.random() * (2 - 0 + 1) + 0);

            b = (int) Math.floor(Math.random() * (2 - 0 + 1) + 0);

            if (tablero[a][b] == 0) {

                pintarNumero(2, a, b);

                System.out.println("pintamos " + a + " " + b);

                ver = true;

            }


        } while (!ver);

        return "" + a + b;

    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }





}
