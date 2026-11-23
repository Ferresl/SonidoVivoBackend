package com.sonidovivo.pedido.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "pedidos")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version private Long version;
    @Column(nullable = false)
    private Long clienteId;
    @Column(nullable = false)
    private String estado;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal impuesto;
    private boolean activo = true;
    private Instant createdAt;
    private Instant updatedAt;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "detalle_pedido", joinColumns = @JoinColumn(name = "pedido_id"))
    private List<Detalle> detalles = new ArrayList<>();
    public BigDecimal getSubtotal() { return detalles.stream().map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad()))).reduce(BigDecimal.ZERO, BigDecimal::add); }
    public BigDecimal getTotal() { return getSubtotal() .add(impuesto); }
    @PrePersist void crearFecha() { createdAt = Instant.now(); updatedAt = createdAt; }
    @PreUpdate void actualizarFecha() { updatedAt = Instant.now(); }
}
