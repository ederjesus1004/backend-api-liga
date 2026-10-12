package dev.eder.liga.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record JugadorResponse(

    Long id,
    String dni,
    String nombres,
    String apellidos,
    String posicion,
    Integer numeroCamiseta,
    Long equipoId,          // NUEVO: para saber a qué equipo pertenece
    String equipoNombre,    // NUEVO: para mostrarlo sin hacer otra petición
    String descripcion,
    LocalDateTime fechaRegistro
) {

}
