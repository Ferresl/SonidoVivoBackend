package com.sonidovivo.carrito.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record CarritoRequestDto(
@NotNull @Positive Long clienteId,
@NotBlank @Size(max = 255)  String estado,
@NotEmpty List<@Valid DetalleDto> detalles
) {
    public record DetalleDto(@NotNull @Positive Long productoId, @NotNull @Positive Integer cantidad, @NotNull @PositiveOrZero @Digits(integer=17, fraction=2) BigDecimal precioUnitario) {
    }
}
