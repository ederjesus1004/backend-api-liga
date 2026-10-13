package dev.eder.liga.service;

import java.util.List;

import dev.eder.liga.dto.JugadorRequest;
import dev.eder.liga.dto.JugadorResponse;

public interface JugadorService {

    List<JugadorResponse> listar();

    List<JugadorResponse> listarPorEquipo(Long equipoId);   // NUEVO

    JugadorResponse obtenerPorId(Long id);

    JugadorResponse crear(JugadorRequest request);

    JugadorResponse actualizar(Long id, JugadorRequest request);

    void eliminar(Long id);
}
