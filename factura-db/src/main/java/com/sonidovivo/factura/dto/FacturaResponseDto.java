package com.sonidovivo.factura.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public record FacturaResponseDto(Long id, Long pedidoId, String numeroFactura, String tipo, BigDecimal subtotal, BigDecimal impuesto, BigDecimal total, String estado, boolean activo, Instant createdAt, Instant updatedAt) {}
