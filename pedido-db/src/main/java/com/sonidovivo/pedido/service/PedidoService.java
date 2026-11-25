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
    private final PedidoRepository repositorio;
    @Transactional(readOnly = true)
    public java.util.List<PedidoResponseDto> listar() {
        return repositorio.findAll().stream().map(this::convertirRespuesta).toList();
    }
    @Transactional(readOnly = true)
    public PedidoResponseDto buscar(Long id) {
        return convertirRespuesta(encontrar(id));
    }
    @Transactional
    public PedidoResponseDto crear(PedidoRequestDto datos) {
        Pedido entidad = new Pedido();
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public PedidoResponseDto actualizar(Long id, PedidoRequestDto datos) {
        Pedido entidad = encontrar(id);
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
    private Pedido encontrar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }
    private void copiar(PedidoRequestDto datos, Pedido entidad) {
        entidad.setClienteId(datos.clienteId());
        entidad.setEstado(datos.estado());
        entidad.setImpuesto(datos.impuesto());
        entidad.getDetalles().clear();
        for (PedidoRequestDto.DetalleDto productoSolicitado : datos.detalles()) {
            Detalle detalle = new Detalle();
            detalle.setProductoId(productoSolicitado.productoId());
            detalle.setCantidad(productoSolicitado.cantidad());
            detalle.setPrecioUnitario(productoSolicitado.precioUnitario());
            entidad.getDetalles().add(detalle);
        }
    }
    private PedidoResponseDto convertirRespuesta(Pedido registro) {
        return new PedidoResponseDto(registro.getId(), registro.getClienteId(), registro.getEstado(), registro.getImpuesto(), registro.isActivo(), registro.getCreatedAt(), registro.getUpdatedAt(), registro.getDetalles().stream().map(detalle -> new PedidoRequestDto.DetalleDto(detalle.getProductoId(), detalle.getCantidad(), detalle.getPrecioUnitario())).toList(), registro.calcularSubtotal(), registro.calcularTotal());
    }
}
