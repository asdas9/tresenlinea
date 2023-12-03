package com.umb.tresenlinea.dto;

public enum EstadoJuego {
    VACIO, X, O;

    @Override
    public String toString() {
        switch(this) {
            case X: return "X";
            case O: return "O";
            case VACIO: return " ";
            default: throw new IllegalArgumentException();
        }
    }
}

