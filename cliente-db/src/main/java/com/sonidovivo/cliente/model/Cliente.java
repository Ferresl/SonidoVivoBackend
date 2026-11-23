package com.sonidovivo.cliente.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "clientes")
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version private Long version;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String correo;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String direccion;
    private boolean activo = true;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist void crearFecha() { createdAt = Instant.now(); updatedAt = createdAt; }
    @PreUpdate void actualizarFecha() { updatedAt = Instant.now(); }
}
