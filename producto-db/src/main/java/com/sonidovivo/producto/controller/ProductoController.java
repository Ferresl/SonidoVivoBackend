package com.sonidovivo.producto.controller;
import com.sonidovivo.producto.dto.*;
import com.sonidovivo.producto.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController @RequestMapping("/api/productos") @RequiredArgsConstructor
@Tag(name = "Producto", description = "Operaciones de productos")
public class ProductoController {
    private final ProductoService servicio;
    @GetMapping
    @Operation(summary = "Listar registros")
    public ResponseEntity<java.util.List<ProductoResponseDto>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }
    @GetMapping("/{id}") public ResponseEntity<ProductoResponseDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.buscar(id));
    }
    @PostMapping
    @Operation(summary = "Crear producto")
    public ResponseEntity<ProductoResponseDto> crear(@Valid @RequestBody ProductoRequestDto datos) {
        ProductoResponseDto creado = servicio.crear(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDto datos) {
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
