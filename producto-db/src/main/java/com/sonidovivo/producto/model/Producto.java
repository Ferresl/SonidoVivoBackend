package com.sonidovivo.producto.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "productos")
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version private Long version;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal precio;
    @Column(nullable = false, unique = true)
    private String sku;
    @Column(nullable = false)
    private String imagenUrl;
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
