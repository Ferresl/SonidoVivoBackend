package com.sonidovivo.pago.service;
import com.sonidovivo.pago.dto.*;
import com.sonidovivo.pago.model.*;
import com.sonidovivo.pago.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@Service @RequiredArgsConstructor
public class PagoService {
    private final PagoRepository repositorio;
    @Transactional(readOnly = true)
    public java.util.List<PagoResponseDto> listar() {
        return repositorio.findAll().stream().map(this::convertirRespuesta).toList();
    }
    @Transactional(readOnly = true)
    public PagoResponseDto buscar(Long id) {
        return convertirRespuesta(encontrar(id));
    }
    @Transactional
    public PagoResponseDto crear(PagoRequestDto datos) {
        Pago entidad = new Pago();
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public PagoResponseDto actualizar(Long id, PagoRequestDto datos) {
        Pago entidad = encontrar(id);
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
    private Pago encontrar(Long id) {
        return repositorio.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
    }
    private void copiar(PagoRequestDto datos, Pago entidad) {
        entidad.setPedidoId(datos.pedidoId());
        entidad.setMetodoPago(datos.metodoPago());
        entidad.setMonto(datos.monto());
        entidad.setEstado(datos.estado());
        entidad.setCodigoTransaccion(datos.codigoTransaccion());
    }
    private PagoResponseDto convertirRespuesta(Pago registro) {
        return new PagoResponseDto(registro.getId(), registro.getPedidoId(), registro.getMetodoPago(), registro.getMonto(), registro.getEstado(), registro.getCodigoTransaccion(), registro.isActivo(), registro.getCreatedAt(), registro.getUpdatedAt());
    }
}
