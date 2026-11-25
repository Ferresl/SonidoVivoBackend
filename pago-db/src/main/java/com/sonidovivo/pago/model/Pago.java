package com.sonidovivo.pago.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "pagos")
public class Pago {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Column(nullable = false)
    private Long pedidoId;
    @Column(nullable = false)
    private String metodoPago;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal monto;
    @Column(nullable = false)
    private String estado;
    @Column(nullable = false, unique = true)
    private String codigoTransaccion;
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
