package com.sonidovivo.pedido.service;
import com.sonidovivo.pedido.dto.*;
import com.sonidovivo.pedido.model.*;
import com.sonidovivo.pedido.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service @RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository repository;

    @Transactional(readOnly = true)
    public java.util.List<PedidoResponseDto> listar() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public PedidoResponseDto buscar(Long id) { return toResponse(encontrar(id)); }

    @Transactional
    public PedidoResponseDto crear(PedidoRequestDto dto) {
        Pedido entity = new Pedido();
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public PedidoResponseDto actualizar(Long id, PedidoRequestDto dto) {
        Pedido entity = encontrar(id);
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) { encontrar(id).setActivo(activo); }

    @Transactional
    public void eliminar(Long id) { encontrar(id).setActivo(false); }

    private Pedido encontrar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado")); }

    private void copiar(PedidoRequestDto dto, Pedido entity) {
        entity.setClienteId(dto.clienteId());
        entity.setEstado(dto.estado());
        entity.setImpuesto(dto.impuesto());

        entity.getDetalles().clear();
        for (PedidoRequestDto.DetalleDto item : dto.detalles()) {
            Detalle d = new Detalle(); d.setProductoId(item.productoId()); d.setCantidad(item.cantidad()); d.setPrecioUnitario(item.precioUnitario()); entity.getDetalles().add(d);
        }
    }

    private PedidoResponseDto toResponse(Pedido e) {
        return new PedidoResponseDto(e.getId(), e.getClienteId(), e.getEstado(), e.getImpuesto(), e.isActivo(), e.getCreatedAt(), e.getUpdatedAt(), e.getDetalles().stream().map(d -> new PedidoRequestDto.DetalleDto(d.getProductoId(), d.getCantidad(), d.getPrecioUnitario())).toList(), e.getSubtotal(), e.getTotal());
    }
}
