package com.sonidovivo.carrito.service;
import com.sonidovivo.carrito.dto.*;
import com.sonidovivo.carrito.model.*;
import com.sonidovivo.carrito.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service @RequiredArgsConstructor
public class CarritoService {
    private final CarritoRepository repository;

    @Transactional(readOnly = true)
    public java.util.List<CarritoResponseDto> listar() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public CarritoResponseDto buscar(Long id) { return toResponse(encontrar(id)); }

    @Transactional
    public CarritoResponseDto crear(CarritoRequestDto dto) {
        Carrito entity = new Carrito();
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public CarritoResponseDto actualizar(Long id, CarritoRequestDto dto) {
        Carrito entity = encontrar(id);
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) { encontrar(id).setActivo(activo); }

    @Transactional
    public void eliminar(Long id) { encontrar(id).setActivo(false); }

    private Carrito encontrar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado")); }

    private void copiar(CarritoRequestDto dto, Carrito entity) {
        entity.setClienteId(dto.clienteId());
        entity.setEstado(dto.estado());

        entity.getDetalles().clear();
        for (CarritoRequestDto.DetalleDto item : dto.detalles()) {
            Detalle d = new Detalle(); d.setProductoId(item.productoId()); d.setCantidad(item.cantidad()); d.setPrecioUnitario(item.precioUnitario()); entity.getDetalles().add(d);
        }
    }

    private CarritoResponseDto toResponse(Carrito e) {
        return new CarritoResponseDto(e.getId(), e.getClienteId(), e.getEstado(), e.isActivo(), e.getCreatedAt(), e.getUpdatedAt(), e.getDetalles().stream().map(d -> new CarritoRequestDto.DetalleDto(d.getProductoId(), d.getCantidad(), d.getPrecioUnitario())).toList(), e.getSubtotal(), e.getTotal());
    }
}
