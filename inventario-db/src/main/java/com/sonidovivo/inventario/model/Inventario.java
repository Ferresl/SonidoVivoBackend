package com.sonidovivo.inventario.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "inventarios")
public class Inventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version private Long version;
    @Column(nullable = false)
    private Long productoId;
    @Column(nullable = false)
    private Integer stockActual;
    @Column(nullable = false)
    private Integer stockReservado;
    @Column(nullable = false)
    private Integer stockMinimo;
    private boolean activo = true;
    private Instant createdAt;
    private Instant updatedAt;
    @PrePersist void crearFecha() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }
    @PreUpdate void actualizarFecha() {
        updatedAt = Instant.now();
    }
}
