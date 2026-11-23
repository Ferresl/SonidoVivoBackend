package com.sonidovivo.carrito.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public record CarritoResponseDto(Long id, Long clienteId, String estado, boolean activo, Instant createdAt, Instant updatedAt, List<CarritoRequestDto.DetalleDto> detalles, BigDecimal subtotal, BigDecimal total) {}
