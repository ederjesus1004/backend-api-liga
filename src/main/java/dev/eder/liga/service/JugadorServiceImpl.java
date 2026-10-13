package dev.eder.liga.service;

import dev.eder.liga.dto.JugadorRequest;
import dev.eder.liga.dto.JugadorResponse;
import dev.eder.liga.model.Equipo;
import dev.eder.liga.model.Jugador;
import dev.eder.liga.exception.RecursoDuplicadoException;
import dev.eder.liga.exception.RecursoNoEncontradoException;
import dev.eder.liga.mapper.JugadorMapper;
import dev.eder.liga.repository.EquipoRespository;
import dev.eder.liga.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoRespository equipoRespository;   // NUEVO: para buscar el padre

    @Override
    @Transactional(readOnly = true)
    public List<JugadorResponse> listar() {
        return jugadorRepository.findAll()
                .stream()
                .map(JugadorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JugadorResponse> listarPorEquipo(Long equipoId) {
        buscarEquipo(equipoId);   // 404 si el equipo no existe (si no, devolvería [] y no sabrías por qué)
        return jugadorRepository.findByEquipoId(equipoId)
                .stream()
                .map(JugadorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public JugadorResponse obtenerPorId(Long id) {
        return JugadorMapper.toResponse(buscarJugador(id));
    }

    @Override
    @Transactional
    public JugadorResponse crear(JugadorRequest request) {
        // 1. Normalizar: no hay email, y el @Pattern ya no deja espacios en el DNI

        // 2. Reglas
        if (jugadorRepository.existsByDni(request.dni())) {
            throw new RecursoDuplicadoException("Ya existe un jugador con el DNI " + request.dni());
        }

        // 3. Buscar el padre (NUEVO)
        Equipo equipo = buscarEquipo(request.equipoId());

        // 4. Mapear
        Jugador jugador = JugadorMapper.toEntity(request, equipo);

        // 5. Guardar y responder
        return JugadorMapper.toResponse(jugadorRepository.save(jugador));
    }

    @Override
    @Transactional
    public JugadorResponse actualizar(Long id, JugadorRequest request) {
        // 0. Buscar al jugador
        Jugador jugador = buscarJugador(id);

        // 2. Reglas: contra OTRO jugador
        if (jugadorRepository.existsByDniAndIdNot(request.dni(), id)) {
            throw new RecursoDuplicadoException("Ya existe un jugador con el DNI " + request.dni());
        }

        // 3. Buscar el padre (puede cambiarse de equipo)
        Equipo equipo = buscarEquipo(request.equipoId());

        // 4. Mapear sobre el que ya existe
        JugadorMapper.actualizarEntidad(request, jugador, equipo);

        // 5. Guardar y responder
        return JugadorMapper.toResponse(jugadorRepository.save(jugador));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Jugador jugador = buscarJugador(id);
        jugadorRepository.delete(jugador);
    }

    private Jugador buscarJugador(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el jugador con id " + id));
    }

    private Equipo buscarEquipo(Long equipoId) {
        return equipoRespository.findById(equipoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el equipo con id " + equipoId));
    }
}