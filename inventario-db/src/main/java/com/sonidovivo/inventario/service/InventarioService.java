package com.sonidovivo.inventario.service;
import com.sonidovivo.inventario.dto.*;
import com.sonidovivo.inventario.model.*;
import com.sonidovivo.inventario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service @RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository repository;

    @Transactional(readOnly = true)
    public java.util.List<InventarioResponseDto> listar() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public InventarioResponseDto buscar(Long id) { return toResponse(encontrar(id)); }

    @Transactional
    public InventarioResponseDto crear(InventarioRequestDto dto) {
        Inventario entity = new Inventario();
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public InventarioResponseDto actualizar(Long id, InventarioRequestDto dto) {
        Inventario entity = encontrar(id);
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) { encontrar(id).setActivo(activo); }

    @Transactional
    public void eliminar(Long id) { encontrar(id).setActivo(false); }

    private Inventario encontrar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario no encontrado")); }

    private void copiar(InventarioRequestDto dto, Inventario entity) {
        entity.setProductoId(dto.productoId());
        entity.setStockActual(dto.stockActual());
        entity.setStockReservado(dto.stockReservado());
        entity.setStockMinimo(dto.stockMinimo());
        if (dto.stockReservado() > dto.stockActual()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock reservado supera stock actual");

    }

    private InventarioResponseDto toResponse(Inventario e) {
        return new InventarioResponseDto(e.getId(), e.getProductoId(), e.getStockActual(), e.getStockReservado(), e.getStockMinimo(), e.isActivo(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
