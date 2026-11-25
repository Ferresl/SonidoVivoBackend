package com.sonidovivo.usuario.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public record UsuarioResponseDto(Long id, Long clienteId, String nombreUsuario, String rol, boolean activo, Instant createdAt, Instant updatedAt) {
}
