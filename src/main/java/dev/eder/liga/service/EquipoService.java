package dev.eder.liga.service;

import java.util.List;

import dev.eder.liga.dto.EquipoRequest;
import dev.eder.liga.dto.EquipoResponse;

public interface EquipoService {

    List<EquipoResponse> listar();

    EquipoResponse obtenerPorId(Long id);

    EquipoResponse crear(EquipoRequest request);

    EquipoResponse actualizar(Long id, EquipoRequest request);

    void eliminar(Long id);
}
