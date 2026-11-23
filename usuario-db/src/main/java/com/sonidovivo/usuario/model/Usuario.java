package com.sonidovivo.usuario.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version private Long version;
    @Column(nullable = false)
    private Long clienteId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String rol;
    private boolean activo = true;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist void crearFecha() { createdAt = Instant.now(); updatedAt = createdAt; }
    @PreUpdate void actualizarFecha() { updatedAt = Instant.now(); }
}
