package com.sonidovivo.producto.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record ProductoRequestDto(
    @NotBlank @Size(max = 255)  String nombre,
    @NotBlank @Size(max = 255)  String marca,
    @NotBlank @Size(max = 2000)  String descripcion,
    @NotNull @PositiveOrZero @Digits(integer = 17, fraction = 2) BigDecimal precio,
    @NotBlank @Size(max = 255)  String sku,
    @NotBlank @Size(max = 255)  String imagenUrl
) {
}
