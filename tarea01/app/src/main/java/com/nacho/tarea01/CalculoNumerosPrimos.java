package com.nacho.tarea01;

import java.util.ArrayList;

/**
 * Created by Nacho on 03/12/2017.
 */

public class CalculoNumerosPrimos {

    ArrayList<Integer> lista = new ArrayList<Integer>();
    int max,k;
    int numPrimo;
    String resultado;
    boolean esPrimo;

    public void CalculoNumerosPrimos(){
    }

    /**
     * Crea el array inicial
     */
    public void crearArray(){

        lista.add(0,1);
        lista.add(1,2);
        lista.add(2,3);
        lista.add(3,5);
        lista.add(4,7);
    }


    /**
     * Para recuperar el resultado
     * @param posicion es el numero primo que queremos calcular
     * @return devuelve el resultado en un string
     */
    public String devolverResultado(int posicion){

        if (posicion <= calculaElementosArray()){
            resultado="El numero primo " + posicion + " es el " + lista.get(posicion-1);
        } else {
            resultado="El número primo " + posicion + " es el " + calcularNumerosPrimos(posicion);
        }
        return resultado;
    }

    /**
     * devuelve el numero del elementos del array
     * @return
     */
    public int calculaElementosArray(){

        return lista.size();

    }

    /**
     * Calcula los numeros primos que no estan en el array
     * @param posicion el numero de numeros que queremos calcular
     * @return nos devuelve el ultimo numero primo calculado
     */
    public int calcularNumerosPrimos(int posicion){

        //bucle hasta llegar al numero solicitado
        for (int i = calculaElementosArray(); i <posicion ; i++) {
            numPrimo=lista.get(i-1)+2;
            esPrimo=false;

            //repite hasta que encontramos un numero primo nuevo
            do {

                boolean a=false;
                //recorre el array para chequear si es primo
                for (int numero : lista) {

                    if (numero > 2) {

                        if (numPrimo % numero == 0) {
                            a=true;
                            break;
                        }

                        if (!a && ((numero*2) > numPrimo)){
                            lista.add(numPrimo);
                            esPrimo=true;
                            break;
                        }

                    }

                }

                numPrimo++;
                numPrimo++;

            }while(!esPrimo);

        }

        return lista.get(lista.size()-1);
    }
}
