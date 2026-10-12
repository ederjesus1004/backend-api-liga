package dev.eder.liga.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EquipoRequest(

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 60, message = "el nombre no puede pasar de 60 caracteres")
    String nombre,

    @NotBlank(message = "La ciudad es obligatorio" )
    @Size(max = 50, message = "La ciudad no puede pasar de 50 caracteres")
    String ciudad
) {

}
