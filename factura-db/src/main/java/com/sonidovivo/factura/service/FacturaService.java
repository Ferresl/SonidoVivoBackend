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
    private final FacturaRepository repositorio;
    @Transactional(readOnly = true)
    public java.util.List<FacturaResponseDto> listar() {
        return repositorio.findAll().stream().map(this::convertirRespuesta).toList();
    }
    @Transactional(readOnly = true)
    public FacturaResponseDto buscar(Long id) {
        return convertirRespuesta(encontrar(id));
    }
    @Transactional
    public FacturaResponseDto crear(FacturaRequestDto datos) {
        Factura entidad = new Factura();
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public FacturaResponseDto actualizar(Long id, FacturaRequestDto datos) {
        Factura entidad = encontrar(id);
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
    private Factura encontrar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrado"));
    }
    private void copiar(FacturaRequestDto datos, Factura entidad) {
        entidad.setPedidoId(datos.pedidoId());
        entidad.setNumeroFactura(datos.numeroFactura());
        entidad.setTipo(datos.tipo());
        entidad.setSubtotal(datos.subtotal());
        entidad.setImpuesto(datos.impuesto());
        entidad.setTotal(datos.total());
        entidad.setEstado(datos.estado());
    }
    private FacturaResponseDto convertirRespuesta(Factura registro) {
        return new FacturaResponseDto(registro.getId(), registro.getPedidoId(), registro.getNumeroFactura(), registro.getTipo(), registro.getSubtotal(), registro.getImpuesto(), registro.getTotal(), registro.getEstado(), registro.isActivo(), registro.getCreatedAt(), registro.getUpdatedAt());
    }
}
