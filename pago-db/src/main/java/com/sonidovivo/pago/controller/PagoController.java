package com.sonidovivo.pago.controller;
import com.sonidovivo.pago.dto.*;
import com.sonidovivo.pago.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController @RequestMapping("/api/pagos") @RequiredArgsConstructor
@Tag(name = "Pago", description = "Operaciones de pagos")
public class PagoController {
    private final PagoService service;

    @GetMapping
    @Operation(summary = "Listar registros activos con paginacion")
    public ResponseEntity<java.util.List<PagoResponseDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}") public ResponseEntity<PagoResponseDto> buscar(@PathVariable Long id) { return ResponseEntity.ok(service.buscar(id)); }

    @PostMapping
    @Operation(summary = "Crear pago")
    public ResponseEntity<PagoResponseDto> crear(@Valid @RequestBody PagoRequestDto dto) {
        PagoResponseDto creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody PagoRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam Boolean activo) {
        service.cambiarEstado(id, activo); return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminar(id); return ResponseEntity.noContent().build(); }
}
