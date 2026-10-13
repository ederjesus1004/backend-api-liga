package dev.eder.liga.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.eder.liga.dto.EquipoRequest;
import dev.eder.liga.dto.EquipoResponse;
import dev.eder.liga.exception.OperacionNoPermitidaException;
import dev.eder.liga.exception.RecursoDuplicadoException;
import dev.eder.liga.exception.RecursoNoEncontradoException;
import dev.eder.liga.mapper.EquipoMapper;
import dev.eder.liga.model.Equipo;
import dev.eder.liga.repository.EquipoRespository;
import dev.eder.liga.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRespository equipoRespository;
    private final JugadorRepository jugadorRepository; // NUEVO: para revisar antes de eliminar

    @Override
    @Transactional(readOnly = true)
    public List<EquipoResponse> listar() {
        return equipoRespository.findAll()
                .stream()
                .map(EquipoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EquipoResponse obtenerPorId(Long id) {
        return EquipoMapper.toResponse(buscarEquipo(id));
    }

    @Override
    @Transactional
    public EquipoResponse crear(EquipoRequest request) {
        // 1. Normalizar
        String nombre = request.nombre().trim();
        // 2. Reglas
        if (equipoRespository.existsByNombre(nombre)) {
            throw new RecursoDuplicadoException("Ya existe un equipo con este nombre " + nombre);
        }
        // 3. Mapear 4. Guardar y responder
        Equipo equipo = EquipoMapper.toEntity(request);
        return EquipoMapper.toResponse(equipoRespository.save(equipo));
    }

    @Override
    @Transactional
    public EquipoResponse actualizar(Long id, EquipoRequest request) {
        Equipo equipo = buscarEquipo(id); // AÑÁDIDO
        String nombre = request.nombre().trim();

        if (equipoRespository.existsByNombreAndIdNot(nombre, id)) { // SE CAMBIO A existsByNombreAndIdNot Y AUMENTO EL
                                                                    // ID
            throw new RecursoDuplicadoException("Ya existe un equipo con este nombre " + nombre);
        }

        EquipoMapper.actualizarEntidad(request, equipo); // SE CAMBIO A MAPPER Y SE ACTUALIZA LA ENTIDAD PORQUE BUSCA
                                                         // POR ID
        return EquipoMapper.toResponse(equipoRespository.save(equipo));
    }

    @Override
    @Transactional
    public void eliminar (Long id){
        Equipo equipo = buscarEquipo(id);
        // NUEVO: no se borra un padre que tiene hijos
        if (jugadorRepository.existsByEquipoId(id)) {
            throw new OperacionNoPermitidaException( "No se puede eliminar el equipo " + equipo.getNombre() + " Porque tiene jugadores registrados");
        }

        equipoRespository.delete(equipo);
    }

    public Equipo buscarEquipo(Long id) {
        return equipoRespository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el equipo con id " + id));
    }
}
