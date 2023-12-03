package com.umb.tresenlinea.dto;

public class Movimiento {
    private int fila;
    private int columna;

    public Movimiento() {
    }

    public Movimiento(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    // Getters y setters
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}

