package com.sonidovivo.cliente.controller;
import com.sonidovivo.cliente.dto.*;
import com.sonidovivo.cliente.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController @RequestMapping("/api/clientes") @RequiredArgsConstructor
@Tag(name = "Cliente", description = "Operaciones de clientes")
public class ClienteController {
    private final ClienteService servicio;
    @GetMapping
    @Operation(summary = "Listar registros")
    public ResponseEntity<java.util.List<ClienteResponseDto>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }
    @GetMapping("/{id}") public ResponseEntity<ClienteResponseDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.buscar(id));
    }
    @PostMapping
    @Operation(summary = "Crear cliente")
    public ResponseEntity<ClienteResponseDto> crear(@Valid @RequestBody ClienteRequestDto datos) {
        ClienteResponseDto creado = servicio.crear(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDto datos) {
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
