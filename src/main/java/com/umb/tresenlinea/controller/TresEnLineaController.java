package com.umb.tresenlinea.controller;

import com.umb.tresenlinea.dto.EstadoJuego;
import com.umb.tresenlinea.dto.Minimax;
import com.umb.tresenlinea.dto.Movimiento;
import com.umb.tresenlinea.dto.Tablero;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/juego")
public class TresEnLineaController {

    private Tablero tablero;
    private Minimax minimax;
    private EstadoJuego jugadorActual;

    public TresEnLineaController() {
        this.tablero = new Tablero();
        this.minimax = new Minimax();
        this.jugadorActual = EstadoJuego.X; // O se puede elegir aleatoriamente quién comienza
    }

    @GetMapping("/iniciar")
    public String iniciarJuego(Model model) {
        this.tablero = new Tablero(); // Reinicia el tablero
        model.addAttribute("tablero", this.tablero.getEstadoTablero());
        return "index";
    }


    @PostMapping("/mover")
    public ResponseEntity<?> realizarMovimiento(@RequestBody Movimiento movimiento) {
        // Verifica si la celda está vacía
        if (tablero.getCasilla(movimiento.getFila(), movimiento.getColumna()) != EstadoJuego.VACIO) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"Movimiento inválido\"}");
        }
        // Realiza el movimiento del jugador
        tablero.realizarJugada(movimiento.getFila(), movimiento.getColumna(), jugadorActual);
        // Verifica si hay un ganador o si es un empate después del movimiento del jugador
        if (hayGanador()) {
            return ResponseEntity.ok("{\"mensaje\": \"Jugador gana\"}");
        } else if (tablero.esEmpate()) {
            return ResponseEntity.ok("{\"mensaje\": \"Empate\"}");
        }
        // Realiza el movimiento de la IA solo si el juego no ha terminado
        Movimiento movimientoIA = minimax.encontrarMejorMovimiento(tablero, EstadoJuego.O);
        tablero.realizarJugada(movimientoIA.getFila(), movimientoIA.getColumna(), EstadoJuego.O);
        // Verifica si hay un ganador o si es un empate después del movimiento de la IA
        if (hayGanador()) {
            return ResponseEntity.ok("{\"mensaje\": \"IA gana\"}");
        } else if (tablero.esEmpate()) {
            return ResponseEntity.ok("{\"mensaje\": \"Empate\"}");
        }
        // Retorna el estado actualizado del juego si nadie ha ganado aún
        return ResponseEntity.ok(tablero.getEstadoTablero());
    }



    private boolean hayGanador() {
        EstadoJuego[][] estado = tablero.getEstadoTablero();

        // Comprobar filas
        for (int i = 0; i < 3; i++) {
            if (estado[i][0] != EstadoJuego.VACIO &&
                    estado[i][0] == estado[i][1] &&
                    estado[i][1] == estado[i][2]) {
                return true;
            }
        }

        // Comprobar columnas
        for (int j = 0; j < 3; j++) {
            if (estado[0][j] != EstadoJuego.VACIO &&
                    estado[0][j] == estado[1][j] &&
                    estado[1][j] == estado[2][j]) {
                return true;
            }
        }

        // Comprobar diagonal principal
        if (estado[0][0] != EstadoJuego.VACIO &&
                estado[0][0] == estado[1][1] &&
                estado[1][1] == estado[2][2]) {
            return true;
        }

        // Comprobar diagonal secundaria
        if (estado[0][2] != EstadoJuego.VACIO &&
                estado[0][2] == estado[1][1] &&
                estado[1][1] == estado[2][0]) {
            return true;
        }

        // No hay ganador
        return false;
    }

}
