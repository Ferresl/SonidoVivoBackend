package com.sonidovivo.inventario.controller;
import com.sonidovivo.inventario.dto.*;
import com.sonidovivo.inventario.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController @RequestMapping("/api/inventarios") @RequiredArgsConstructor
@Tag(name = "Inventario", description = "Operaciones de inventarios")
public class InventarioController {
    private final InventarioService servicio;
    @GetMapping
    @Operation(summary = "Listar registros")
    public ResponseEntity<java.util.List<InventarioResponseDto>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }
    @GetMapping("/{id}") public ResponseEntity<InventarioResponseDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.buscar(id));
    }
    @PostMapping
    @Operation(summary = "Crear inventario")
    public ResponseEntity<InventarioResponseDto> crear(@Valid @RequestBody InventarioRequestDto datos) {
        InventarioResponseDto creado = servicio.crear(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    @PutMapping("/{id}")
    public ResponseEntity<InventarioResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody InventarioRequestDto datos) {
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
