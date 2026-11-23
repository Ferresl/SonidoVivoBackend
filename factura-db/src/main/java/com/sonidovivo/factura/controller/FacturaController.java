package com.sonidovivo.factura.controller;
import com.sonidovivo.factura.dto.*;
import com.sonidovivo.factura.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController @RequestMapping("/api/facturas") @RequiredArgsConstructor
@Tag(name = "Factura", description = "Operaciones de facturas")
public class FacturaController {
    private final FacturaService service;

    @GetMapping
    @Operation(summary = "Listar registros activos con paginacion")
    public ResponseEntity<java.util.List<FacturaResponseDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}") public ResponseEntity<FacturaResponseDto> buscar(@PathVariable Long id) { return ResponseEntity.ok(service.buscar(id)); }

    @PostMapping
    @Operation(summary = "Crear factura")
    public ResponseEntity<FacturaResponseDto> crear(@Valid @RequestBody FacturaRequestDto dto) {
        FacturaResponseDto creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody FacturaRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam Boolean activo) {
        service.cambiarEstado(id, activo); return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminar(id); return ResponseEntity.noContent().build(); }
}
