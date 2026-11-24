package com.sonidovivo.producto.dto;
import java.math.BigDecimal;
import java.time.Instant;
public record ProductoResponseDto(
    Long id, 
    String nombre, 
    String marca, 
    String descripcion, 
    BigDecimal precio, 
    String sku,
    boolean activo, 
    Instant createdAt, 
    Instant updatedAt) {}
