package com.umb.tresenlinea.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minimax {

    // Método para encontrar el mejor movimiento
    public Movimiento encontrarMejorMovimiento(Tablero tablero, EstadoJuego jugadorActual) {
        List<Movimiento> mejoresMovimientos = new ArrayList<>();
        int mejorValor = Integer.MIN_VALUE;

        // Recorre todas las celdas del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Verifica si la celda está vacía
                if (tablero.getCasilla(i, j) == EstadoJuego.VACIO) {
                    // Realiza el movimiento
                    tablero.realizarJugada(i, j, jugadorActual);

                    // Si el jugador actual gana con este movimiento, devuélvelo de inmediato
                    if (tablero.haGanado(jugadorActual)) {
                        tablero.desHacerJugada(i, j);
                        return new Movimiento(i, j);
                    }

                    // Calcula el valor del movimiento
                    int valorMovimiento = minimax(tablero, 0, false, jugadorActual);

                    // Deshace el movimiento
                    tablero.desHacerJugada(i, j);

                    // Si el valor es igual al mejor valor, agrégalo a la lista de mejores movimientos
                    if (valorMovimiento == mejorValor) {
                        mejoresMovimientos.add(new Movimiento(i, j));
                    }
                    // Si el valor es mejor, actualiza la lista de mejores movimientos
                    else if (valorMovimiento > mejorValor) {
                        mejoresMovimientos.clear();
                        mejoresMovimientos.add(new Movimiento(i, j));
                        mejorValor = valorMovimiento;
                    }
                }
            }
        }

        // Elige aleatoriamente uno de los mejores movimientos
        if (!mejoresMovimientos.isEmpty()) {
            Random random = new Random();
            return mejoresMovimientos.get(random.nextInt(mejoresMovimientos.size()));
        }
        // Si no hay movimientos disponibles (por ejemplo, tablero completo), devuelve un movimiento inválido
        return new Movimiento(-1, -1);
    }

    // Algoritmo Minimax con Alpha-Beta Pruning
    private int minimax(Tablero tablero, int profundidad, boolean esMaximizador, EstadoJuego jugadorActual) {
        int valorTablero = evaluar(tablero);

        // Verifica si se alcanzó la profundidad máxima o si el juego ha terminado
        if (valorTablero != 0 || profundidad == 3) {
            return valorTablero;
        }

        if (esMaximizador) {
            int mejor = Integer.MIN_VALUE;

            // Recorre todas las celdas
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Verifica si la celda está vacía
                    if (tablero.getCasilla(i, j) == EstadoJuego.VACIO) {
                        // Realiza el movimiento
                        tablero.realizarJugada(i, j, jugadorActual);

                        // Si el jugador actual gana con este movimiento, devuélvelo de inmediato
                        if (tablero.haGanado(jugadorActual)) {
                            tablero.desHacerJugada(i, j);
                            return Integer.MAX_VALUE; // Este es un valor alto para favorecer el movimiento ganador
                        }

                        // Llama a minimax recursivamente y elige el máximo valor
                        int valorMovimiento = minimax(tablero, profundidad + 1, false, jugadorActual);
                        mejor = Math.max(mejor, valorMovimiento);

                        // Deshace el movimiento
                        tablero.desHacerJugada(i, j);
                    }
                }
            }
            return mejor;
        } else {
            int mejor = Integer.MAX_VALUE;

            // Recorre todas las celdas
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Verifica si la celda está vacía
                    if (tablero.getCasilla(i, j) == EstadoJuego.VACIO) {
                        // Realiza el movimiento
                        EstadoJuego siguienteJugador = jugadorActual == EstadoJuego.X ? EstadoJuego.O : EstadoJuego.X;
                        tablero.realizarJugada(i, j, siguienteJugador);

                        // Si el jugador siguiente gana con este movimiento, devuélvelo de inmediato
                        if (tablero.haGanado(siguienteJugador)) {
                            tablero.desHacerJugada(i, j);
                            return Integer.MIN_VALUE; // Este es un valor bajo para evitar el movimiento perdedor
                        }

                        // Llama a minimax recursivamente y elige el mínimo valor
                        int valorMovimiento = minimax(tablero, profundidad + 1, true, jugadorActual);
                        mejor = Math.min(mejor, valorMovimiento);

                        // Deshace el movimiento
                        tablero.desHacerJugada(i, j);
                    }
                }
            }
            return mejor;
        }
    }

    // Método para evaluar el estado actual del tablero
    private int evaluar(Tablero tablero) {
        // Comprobar filas para la victoria
        for (int fila = 0; fila < 3; fila++) {
            if (sonIguales(tablero.getCasilla(fila, 0), tablero.getCasilla(fila, 1), tablero.getCasilla(fila, 2))) {
                return valorDe(tablero.getCasilla(fila, 0));
            }
        }

        // Comprobar columnas para la victoria
        for (int columna = 0; columna < 3; columna++) {
            if (sonIguales(tablero.getCasilla(0, columna), tablero.getCasilla(1, columna), tablero.getCasilla(2, columna))) {
                return valorDe(tablero.getCasilla(0, columna));
            }
        }

        // Comprobar diagonales para la victoria
        if (sonIguales(tablero.getCasilla(0, 0), tablero.getCasilla(1, 1), tablero.getCasilla(2, 2)) ||
                sonIguales(tablero.getCasilla(0, 2), tablero.getCasilla(1, 1), tablero.getCasilla(2, 0))) {
            return valorDe(tablero.getCasilla(1, 1));
        }

        // Si no hay ganador
        return 0;
    }

    // Método auxiliar para comprobar si tres celdas son iguales
    private boolean sonIguales(EstadoJuego c1, EstadoJuego c2, EstadoJuego c3) {
        return c1 != EstadoJuego.VACIO && c1 == c2 && c2 == c3;
    }

    // Método auxiliar para determinar el valor de un estado de juego
    private int valorDe(EstadoJuego estado) {
        if (estado == EstadoJuego.X) {
            return +10;
        } else if (estado == EstadoJuego.O) {
            return -10;
        }
        return 0;
    }
}
