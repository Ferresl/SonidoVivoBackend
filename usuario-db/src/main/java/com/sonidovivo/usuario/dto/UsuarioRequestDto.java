package com.sonidovivo.usuario.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record UsuarioRequestDto(
@NotNull @Positive Long clienteId,
@NotBlank @Size(max = 255)  String nombreUsuario,
@NotBlank @Size(max = 255)  String contrasena
) {
}
