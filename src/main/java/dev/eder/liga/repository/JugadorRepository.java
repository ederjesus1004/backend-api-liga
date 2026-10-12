package dev.eder.liga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eder.liga.model.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador,Long>{

    boolean existsByDni(String dni);
    boolean existsByDniAndIdNot(String nombre);

    // NUEVO: buscan por el id del equipo
    List<Jugador> findByEquipoId(Long equipoId); // los jugadores de un equipo
    boolean existsByEquipoId(Long equipoId);  // ¿el equipo tiene al menos un jugador?

}
