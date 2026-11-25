package com.sonidovivo.notificacion.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public record NotificacionResponseDto(Long id, Long usuarioId, String titulo, String mensaje, String tipo, Boolean leida, boolean activo, Instant createdAt, Instant updatedAt) {
}
