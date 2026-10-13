package dev.eder.liga.mapper;

import dev.eder.liga.dto.EquipoRequest;
import dev.eder.liga.dto.EquipoResponse;
import dev.eder.liga.model.Equipo;

public final class EquipoMapper {

    private EquipoMapper(){
    }

    public static Equipo toEntity(EquipoRequest request){
        Equipo equipo = new Equipo();
        actualizarEntidad(request, equipo);
        return equipo;
    }

    public static  void actualizarEntidad(EquipoRequest request, Equipo equipo){
        equipo.setNombre(request.nombre().trim());
        equipo.setCiudad(request.ciudad().trim());
    }

    public static EquipoResponse toResponse(Equipo equipo){
        return EquipoResponse.builder()
        .id(equipo.getId())
        .nombre(equipo.getNombre())
        .ciudad(equipo.getCiudad())
        .build();
    }
}
