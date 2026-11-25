package com.sonidovivo.cliente.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record ClienteRequestDto(
@NotBlank @Size(max = 255)  String nombre,
@NotBlank @Size(max = 255) @Email  String correo,
@NotBlank @Size(max = 255)  String telefono,
@NotBlank @Size(max = 255)  String direccion
) {
}
