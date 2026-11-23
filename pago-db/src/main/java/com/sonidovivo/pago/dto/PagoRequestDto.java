package com.sonidovivo.pago.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record PagoRequestDto(
    @NotNull @Positive Long pedidoId,
    @NotBlank @Size(max = 255)  String metodoPago,
    @NotNull @PositiveOrZero @Digits(integer = 17, fraction = 2) BigDecimal monto,
    @NotBlank @Size(max = 255)  String estado,
    @NotBlank @Size(max = 255)  String codigoTransaccion
) {
}
