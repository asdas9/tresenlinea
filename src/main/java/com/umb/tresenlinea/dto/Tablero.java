package com.umb.tresenlinea.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tablero {
    private EstadoJuego[][] tablero;

    public EstadoJuego[][] getEstadoTablero() {
        return tablero;
    }
    public Tablero() {
        tablero = new EstadoJuego[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = EstadoJuego.VACIO;
            }
        }
    }

    public void realizarJugada(int fila, int columna, EstadoJuego jugador) {
        tablero[fila][columna] = jugador;
    }

    public void desHacerJugada(int fila, int columna) {
        tablero[fila][columna] = EstadoJuego.VACIO;
    }

    public EstadoJuego getCasilla(int fila, int columna) {
        if (fila >= 0 && fila < tablero.length && columna >= 0 && columna < tablero[fila].length) {
            return tablero[fila][columna];
        } else {
            throw new IllegalArgumentException("Índice fuera de límites");
        }
    }

    public boolean haGanado(EstadoJuego jugador) {
        // Comprobar filas para la victoria
        for (int fila = 0; fila < 3; fila++) {
            if (sonIguales(jugador, tablero[fila][0], tablero[fila][1], tablero[fila][2])) {
                return true;
            }
        }

        // Comprobar columnas para la victoria
        for (int columna = 0; columna < 3; columna++) {
            if (sonIguales(jugador, tablero[0][columna], tablero[1][columna], tablero[2][columna])) {
                return true;
            }
        }

        // Comprobar diagonales para la victoria
        if (sonIguales(jugador, tablero[0][0], tablero[1][1], tablero[2][2]) ||
                sonIguales(jugador, tablero[0][2], tablero[1][1], tablero[2][0])) {
            return true;
        }

        return false;
    }

    public boolean sonIguales(EstadoJuego jugador, EstadoJuego c1, EstadoJuego c2, EstadoJuego c3) {
        return c1 == jugador && c2 == jugador && c3 == jugador;
    }

    public boolean esEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == EstadoJuego.VACIO) {
                    return false;
                }
            }
        }
        return true;
    }
}



