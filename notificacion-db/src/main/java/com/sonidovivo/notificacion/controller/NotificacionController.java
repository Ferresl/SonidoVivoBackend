package com.sonidovivo.notificacion.controller;
import com.sonidovivo.notificacion.dto.*;
import com.sonidovivo.notificacion.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController @RequestMapping("/api/notificaciones") @RequiredArgsConstructor
@Tag(name = "Notificacion", description = "Operaciones de notificaciones")
public class NotificacionController {
    private final NotificacionService service;

    @GetMapping
    @Operation(summary = "Listar registros activos con paginacion")
    public ResponseEntity<java.util.List<NotificacionResponseDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}") public ResponseEntity<NotificacionResponseDto> buscar(@PathVariable Long id) { return ResponseEntity.ok(service.buscar(id)); }

    @PostMapping
    @Operation(summary = "Crear notificacion")
    public ResponseEntity<NotificacionResponseDto> crear(@Valid @RequestBody NotificacionRequestDto dto) {
        NotificacionResponseDto creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody NotificacionRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam Boolean activo) {
        service.cambiarEstado(id, activo); return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminar(id); return ResponseEntity.noContent().build(); }
}
