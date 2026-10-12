package dev.eder.liga.dto;

import lombok.Builder;

@Builder
public record EquipoResponse(
    
    Long id,
    String nombre,
    String ciudad

) {

}
