package com.sonidovivo.factura.service;
import com.sonidovivo.factura.dto.*;
import com.sonidovivo.factura.model.*;
import com.sonidovivo.factura.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service @RequiredArgsConstructor
public class FacturaService {
    private final FacturaRepository repository;

    @Transactional(readOnly = true)
    public java.util.List<FacturaResponseDto> listar() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public FacturaResponseDto buscar(Long id) { return toResponse(encontrar(id)); }

    @Transactional
    public FacturaResponseDto crear(FacturaRequestDto dto) {
        Factura entity = new Factura();
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public FacturaResponseDto actualizar(Long id, FacturaRequestDto dto) {
        Factura entity = encontrar(id);
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) { encontrar(id).setActivo(activo); }

    @Transactional
    public void eliminar(Long id) { encontrar(id).setActivo(false); }

    private Factura encontrar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrado")); }

    private void copiar(FacturaRequestDto dto, Factura entity) {
        entity.setPedidoId(dto.pedidoId());
        entity.setNumeroFactura(dto.numeroFactura());
        entity.setTipo(dto.tipo());
        entity.setSubtotal(dto.subtotal());
        entity.setImpuesto(dto.impuesto());
        entity.setTotal(dto.total());
        entity.setEstado(dto.estado());


    }

    private FacturaResponseDto toResponse(Factura e) {
        return new FacturaResponseDto(e.getId(), e.getPedidoId(), e.getNumeroFactura(), e.getTipo(), e.getSubtotal(), e.getImpuesto(), e.getTotal(), e.getEstado(), e.isActivo(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
