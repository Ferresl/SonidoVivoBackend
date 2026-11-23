package com.sonidovivo.factura.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record FacturaRequestDto(
    @NotNull @Positive Long pedidoId,
    @NotBlank @Size(max = 255)  String numeroFactura,
    @NotBlank @Size(max = 255)  String tipo,
    @NotNull @PositiveOrZero @Digits(integer = 17, fraction = 2) BigDecimal subtotal,
    @NotNull @PositiveOrZero @Digits(integer = 17, fraction = 2) BigDecimal impuesto,
    @NotNull @PositiveOrZero @Digits(integer = 17, fraction = 2) BigDecimal total,
    @NotBlank @Size(max = 255)  String estado
) {
}
