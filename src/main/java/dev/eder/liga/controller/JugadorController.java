package dev.eder.liga.controller;

import dev.eder.liga.dto.JugadorRequest;
import dev.eder.liga.dto.JugadorResponse;
import dev.eder.liga.service.JugadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
public class JugadorController {

    private final JugadorService jugadorService;

    @GetMapping
    public List<JugadorResponse> listar() {
        return jugadorService.listar();
    }

    // NUEVO: GET /api/jugadores/equipo/1 → los jugadores del equipo 1
    @GetMapping("/equipo/{equipoId}")
    public List<JugadorResponse> listarPorEquipo(@PathVariable Long equipoId) {
        return jugadorService.listarPorEquipo(equipoId);
    }

    @GetMapping("/{id}")
    public JugadorResponse obtenerPorId(@PathVariable Long id) {
        return jugadorService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JugadorResponse crear(@Valid @RequestBody JugadorRequest request) {
        return jugadorService.crear(request);
    }

    @PutMapping("/{id}")
    public JugadorResponse actualizar(@PathVariable Long id, @Valid @RequestBody JugadorRequest request) {
        return jugadorService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        jugadorService.eliminar(id);
    }
}