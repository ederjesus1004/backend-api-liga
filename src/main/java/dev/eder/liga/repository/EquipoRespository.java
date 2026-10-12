package dev.eder.liga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eder.liga.model.Equipo;

public interface EquipoRespository extends JpaRepository<Equipo, Long>{

    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre, Long id);
}
