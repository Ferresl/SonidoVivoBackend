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
    private final CarritoRepository repositorio;
    @Transactional(readOnly = true)
    public java.util.List<CarritoResponseDto> listar() {
        return repositorio.findAll().stream().map(this::convertirRespuesta).toList();
    }
    @Transactional(readOnly = true)
    public CarritoResponseDto buscar(Long id) {
        return convertirRespuesta(encontrar(id));
    }
    @Transactional
    public CarritoResponseDto crear(CarritoRequestDto datos) {
        Carrito entidad = new Carrito();
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public CarritoResponseDto actualizar(Long id, CarritoRequestDto datos) {
        Carrito entidad = encontrar(id);
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public void cambiarEstado(Long id, boolean activo) {
        encontrar(id).setActivo(activo);
    }
    @Transactional
    public void eliminar(Long id) {
        encontrar(id).setActivo(false);
    }
    private Carrito encontrar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));
    }
    private void copiar(CarritoRequestDto datos, Carrito entidad) {
        entidad.setClienteId(datos.clienteId());
        entidad.setEstado(datos.estado());
        entidad.getDetalles().clear();
        for (CarritoRequestDto.DetalleDto productoSolicitado : datos.detalles()) {
            Detalle detalle = new Detalle();
            detalle.setProductoId(productoSolicitado.productoId());
            detalle.setCantidad(productoSolicitado.cantidad());
            detalle.setPrecioUnitario(productoSolicitado.precioUnitario());
            entidad.getDetalles().add(detalle);
        }
    }
    private CarritoResponseDto convertirRespuesta(Carrito registro) {
        return new CarritoResponseDto(registro.getId(), registro.getClienteId(), registro.getEstado(), registro.isActivo(), registro.getCreatedAt(), registro.getUpdatedAt(), registro.getDetalles().stream().map(detalle -> new CarritoRequestDto.DetalleDto(detalle.getProductoId(), detalle.getCantidad(), detalle.getPrecioUnitario())).toList(), registro.calcularSubtotal(), registro.calcularTotal());
    }
}
