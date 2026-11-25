package com.sonidovivo.notificacion.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "notificaciones")
public class Notificacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version private Long version;
    @Column(nullable = false)
    private Long usuarioId;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String mensaje;
    @Column(nullable = false)
    private String tipo;
    @Column(nullable = false)
    private Boolean leida;
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
