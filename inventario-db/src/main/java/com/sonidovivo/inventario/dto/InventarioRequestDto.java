package com.sonidovivo.inventario.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record InventarioRequestDto(
    @NotNull @Positive Long productoId,
    @NotNull @PositiveOrZero Integer stockActual,
    @NotNull @PositiveOrZero Integer stockReservado,
    @NotNull @PositiveOrZero Integer stockMinimo
) {
}
