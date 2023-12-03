package com.umb.tresenlinea.dto;

import lombok.Builder;

@Builder
public class Respuesta {
    private String mensaje;
    private EstadoJuego[][] tablero;
}
