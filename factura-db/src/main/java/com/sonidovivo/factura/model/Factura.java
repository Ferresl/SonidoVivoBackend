package com.sonidovivo.factura.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "facturas")
public class Factura {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version private Long version;
    @Column(nullable = false)
    private Long pedidoId;
    @Column(nullable = false, unique = true)
    private String numeroFactura;
    @Column(nullable = false)
    private String tipo;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal subtotal;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal impuesto;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal total;
    @Column(nullable = false)
    private String estado;
    private boolean activo = true;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist void crearFecha() { createdAt = Instant.now(); updatedAt = createdAt; }
    @PreUpdate void actualizarFecha() { updatedAt = Instant.now(); }
}
