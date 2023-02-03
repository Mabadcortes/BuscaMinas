package buscaminas;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;

public class BuscaMinas {

    public static void mostrarArray(int[][] array) {
        for (int fila = 0; fila < array.length; fila++) {
            System.out.println("");
            for (int columna = 0; columna < array[fila].length; columna++) {
                System.out.print(array[fila][columna] + "\t");
            }
        }
    }

    private static int cantidadBombas(int bombas) throws NumberFormatException, HeadlessException {
        do {
            bombas = Integer.parseInt(JOptionPane.showInputDialog("Introduce la cantidas de bombas que quieres (min:5/max:32)"));
        } while (bombas < 5 || bombas > 32);
        return bombas;
    }

    private static void coordenadas(int[][] copia, int[][] buscaMinas) throws HeadlessException, NumberFormatException {
        int fila;
        int columna;
        do {
            do {
                System.out.println("\n");

                fila = Integer.parseInt(JOptionPane.showInputDialog("Introduce a que coordenada de eje Y quieres desplazarte"));
            } while (fila < 0 || fila > 7);
            do {
                columna = Integer.parseInt(JOptionPane.showInputDialog("Introduce a que coordenada de eje X quieres desplazarte"));
            } while (columna < 0 || columna > 7);
            mostrarArray(copia);
            posicionJugador(buscaMinas, fila, columna);
            copia[fila][columna] = 1;
        } while (finPartida(buscaMinas[fila][columna]) == false);
    }

    public static boolean finPartida(int num) {
        boolean fin = false;
        if (num == 10 || num == -1) {
            fin = true;
        }
        return fin;
    }

    public static int[][] generarBombasTesoro(int[][] array, int bombas) {
        int fila, columna;
        boolean repetido = false;
        for (int cont = 0; cont <= bombas; cont++) {
            do {
                repetido = false;
                fila = (int) (Math.random() * array.length);
                columna = (int) (Math.random() * array.length);
                if (array[fila][columna] == -1) {
                    repetido = true;
                }
            } while (repetido == true);
            if (cont == bombas) {
                array[fila][columna] = 10;
            } else {
                array[fila][columna] = -1;
            }
        }
        return array;
    }

    public static void posicionJugador(int[][] array, int fila, int columna) {
        int bombas = 0;
        int y = fila - 1, x = columna - 1;
        boolean bomba = false;
        boolean maxX = false;
        boolean maxY = false;
        boolean fin = false;

        if (y < 0) {
            y = 0;
        }
        if (x < 0) {
            x = 0;
        }
        for (; y <= fila + 1 && maxY == false && fin == false; y++) {
            maxX = false;
            for (; x <= columna + 1 && maxX == false && fin == false; x++) {
                if (x > array.length) {
                    maxX = true;
                    x = 7;
                }
                if (array[y][x] == -1) {
                    bomba = true;
                }
                fin = hola(array, fila, columna, fin);
            }
            if (y >= array.length) {
                maxY = true;
                y = 7;
            }
        }
        if (bomba && fin == false) {
            System.out.println("\nTienes alguna bomba cerca");
        }
    }

    private static boolean hola(int[][] array, int fila, int columna, boolean fin) {
        if (array[fila][columna] == -1) {
            fin = true;
            System.out.println("\nHas pisado una mina, has perdido :(");
        }
        if (array[fila][columna] == 10) {
            fin = true;
            System.out.println("\n¡Has encontrado el tesoro! has ganado :)");
        }
        return fin;
    }

    public static void main(String[] args) {
        int[][] buscaMinas = new int[8][8];
        int[][] copia = new int[8][8];
        int bombas = 0, columna, fila;

        bombas = cantidadBombas(bombas);
        buscaMinas = generarBombasTesoro(buscaMinas, bombas);
        coordenadas(copia, buscaMinas);
        mostrarArray(buscaMinas);
    }
}
