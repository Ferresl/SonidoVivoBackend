package com.sonidovivo.pedido.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
@Embeddable @Getter @Setter @NoArgsConstructor
public class Detalle {
    private Long productoId;
    private Integer cantidad;
    @Column(precision = 19, scale = 2) private BigDecimal precioUnitario;
}
