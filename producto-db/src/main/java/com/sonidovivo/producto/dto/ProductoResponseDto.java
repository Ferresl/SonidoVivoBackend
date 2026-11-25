package com.sonidovivo.producto.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public record ProductoResponseDto(Long id, String nombre, String marca, String descripcion, BigDecimal precio, String sku, String imagenUrl, boolean activo, Instant createdAt, Instant updatedAt) {
}
