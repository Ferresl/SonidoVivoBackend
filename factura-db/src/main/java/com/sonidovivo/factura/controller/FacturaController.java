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
    private final FacturaService servicio;
    @GetMapping
    @Operation(summary = "Listar registros")
    public ResponseEntity<java.util.List<FacturaResponseDto>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }
    @GetMapping("/{id}") public ResponseEntity<FacturaResponseDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.buscar(id));
    }
    @PostMapping
    @Operation(summary = "Crear factura")
    public ResponseEntity<FacturaResponseDto> crear(@Valid @RequestBody FacturaRequestDto datos) {
        FacturaResponseDto creado = servicio.crear(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FacturaResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody FacturaRequestDto datos) {
        return ResponseEntity.ok(servicio.actualizar(id, datos));
    }
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam Boolean activo) {
        servicio.cambiarEstado(id, activo);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
