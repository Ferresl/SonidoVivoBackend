package com.sonidovivo.usuario.controller;
import com.sonidovivo.usuario.dto.*;
import com.sonidovivo.usuario.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController @RequestMapping("/api/usuarios") @RequiredArgsConstructor
@Tag(name = "Usuario", description = "Operaciones de usuarios")
public class UsuarioController {
    private final UsuarioService servicio;
    @GetMapping
    @Operation(summary = "Listar registros")
    public ResponseEntity<java.util.List<UsuarioResponseDto>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }
    @GetMapping("/{id}") public ResponseEntity<UsuarioResponseDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.buscar(id));
    }
    @PostMapping
    @Operation(summary = "Crear usuario")
    public ResponseEntity<UsuarioResponseDto> crear(@Valid @RequestBody UsuarioRequestDto datos) {
        UsuarioResponseDto creado = servicio.crear(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDto datos) {
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
