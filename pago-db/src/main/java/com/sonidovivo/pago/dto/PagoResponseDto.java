package com.sonidovivo.pago.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public record PagoResponseDto(Long id, Long pedidoId, String metodoPago, BigDecimal monto, String estado, String codigoTransaccion, boolean activo, Instant createdAt, Instant updatedAt) {
}
