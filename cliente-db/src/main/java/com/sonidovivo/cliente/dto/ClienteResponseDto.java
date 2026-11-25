package com.sonidovivo.cliente.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public record ClienteResponseDto(Long id, String nombre, String correo, String telefono, String direccion, boolean activo, Instant createdAt, Instant updatedAt) {
}
