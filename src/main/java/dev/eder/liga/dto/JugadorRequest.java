package dev.eder.liga.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record JugadorRequest(

    @NotBlank(message = "El Dni es obligatorio")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI tiene que tener 8 digitos")
    String dni,

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "Los nombres no pueden pasar de 80 caracteres")
    String nombres,

    @NotBlank(message = "El apellidos es obligatorio")
    @Size(max = 80, message = "Los apellidos no pueden pasar de 80 caracteres")
    String apellidos,
    
    @NotBlank(message = "La posicion es obligatorio")
    @Size(max = 20, message = "La posición no puede pasar de 20 caracteres")
    String posicion,

    @NotNull(message = "El numero de camiseta es obligatorio")
    @Min(value = 1, message = "El numero minimo es de 1")
    @Max(value = 99, message = "El numero maximo es de 99")
    Integer numeroCamiseta,
    
    // NUEVO: solo el id del equipo, no el objeto
    @NotNull(message = "El equipo es boligatorio")
    Long equipoId

) {

}
