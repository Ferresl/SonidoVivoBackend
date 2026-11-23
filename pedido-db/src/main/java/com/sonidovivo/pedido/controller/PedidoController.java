package com.sonidovivo.pedido.controller;
import com.sonidovivo.pedido.dto.*;
import com.sonidovivo.pedido.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController @RequestMapping("/api/pedidos") @RequiredArgsConstructor
@Tag(name = "Pedido", description = "Operaciones de pedidos")
public class PedidoController {
    private final PedidoService service;

    @GetMapping
    @Operation(summary = "Listar registros activos con paginacion")
    public ResponseEntity<java.util.List<PedidoResponseDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}") public ResponseEntity<PedidoResponseDto> buscar(@PathVariable Long id) { return ResponseEntity.ok(service.buscar(id)); }

    @PostMapping
    @Operation(summary = "Crear pedido")
    public ResponseEntity<PedidoResponseDto> crear(@Valid @RequestBody PedidoRequestDto dto) {
        PedidoResponseDto creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam Boolean activo) {
        service.cambiarEstado(id, activo); return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminar(id); return ResponseEntity.noContent().build(); }
}
