package dev.eder.liga.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jugadores")
@Data
@NoArgsConstructor
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;
    
    @Column(nullable = false, length = 80)
    private String nombre;
    
    @Column(nullable = false, length = 80)
    private String apllidos;

    @Column(nullable = false, length = 20)
    private String posicion;

    @Column(name = "numero_camiseta", nullable = false)
    private Integer numeroCamiseta;

    // NUEVO: muchos jugadores pertenecen a UN equipo
    @ManyToAny(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;
    
    @PrePersist
    void alCrear(){
        this.fechaRegistro = LocalDateTime.now();
    }
}
