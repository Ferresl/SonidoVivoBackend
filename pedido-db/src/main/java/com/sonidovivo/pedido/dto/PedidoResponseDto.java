package com.sonidovivo.pedido.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public record PedidoResponseDto(Long id, Long clienteId, String estado, BigDecimal impuesto, boolean activo, Instant createdAt, Instant updatedAt, List<PedidoRequestDto.DetalleDto> detalles, BigDecimal subtotal, BigDecimal total) {}
