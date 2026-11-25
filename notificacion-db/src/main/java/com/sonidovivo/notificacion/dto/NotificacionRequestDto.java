package com.sonidovivo.notificacion.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record NotificacionRequestDto(
@NotNull @Positive Long usuarioId,
@NotBlank @Size(max = 255)  String titulo,
@NotBlank @Size(max = 2000)  String mensaje,
@NotBlank @Size(max = 255)  String tipo,
@NotNull Boolean leida
) {
}
