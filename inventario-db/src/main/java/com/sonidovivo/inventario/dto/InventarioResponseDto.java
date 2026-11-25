package com.sonidovivo.inventario.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public record InventarioResponseDto(Long id, Long productoId, Integer stockActual, Integer stockReservado, Integer stockMinimo, boolean activo, Instant createdAt, Instant updatedAt) {
}
