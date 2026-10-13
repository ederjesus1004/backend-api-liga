package dev.eder.liga.mapper;

import dev.eder.liga.dto.JugadorRequest;
import dev.eder.liga.dto.JugadorResponse;
import dev.eder.liga.model.Equipo;
import dev.eder.liga.model.Jugador;

public final class JugadorMapper {

    private JugadorMapper(){
    }

    // NUEVO: recibe también el equipo que el service ya buscó
    public static Jugador toEntity(JugadorRequest request, Equipo equipo){
        Jugador jugador= new Jugador();
        actualizarEntidad(request, jugador, equipo);
        return jugador;
    }

    public static void actualizarEntidad(JugadorRequest request, Jugador jugador, Equipo equipo){
        jugador.setDni(request.dni().trim());
        jugador.setNombres(request.nombres().trim());
        jugador.setApellidos(request.apellidos().trim());
        jugador.setPosicion(request.posicion().trim());
        jugador.setNumeroCamiseta(request.numeroCamiseta());
        jugador.setEquipo(equipo); // NUEVO: el objeto completo, no el id
    }

    public static JugadorResponse toResponse(Jugador jugador){
        Equipo equipo = jugador.getEquipo();    // aquí JPA trae el equipo (LAZY)
        return JugadorResponse.builder()
        .id(jugador.getId())
        .dni(jugador.getDni())
        .nombres(jugador.getNombres())
        .apellidos(jugador.getApellidos())
        .posicion(jugador.getPosicion())
        .numeroCamiseta(jugador.getNumeroCamiseta())
        .equipoId(equipo.getId())
        .equipoNombre(equipo.getNombre())
        // "Luis Ramos #10 - Deportivo Rímac"
        .descripcion(jugador.getNombres() + " " + jugador.getApellidos()
                + " #" + jugador.getNumeroCamiseta() + " - " + equipo.getNombre())
        .fechaRegistro(jugador.getFechaRegistro())
        .build();
    }
}
