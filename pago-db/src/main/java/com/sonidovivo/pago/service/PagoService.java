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
    private final PagoRepository repository;

    @Transactional(readOnly = true)
    public java.util.List<PagoResponseDto> listar() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public PagoResponseDto buscar(Long id) { return toResponse(encontrar(id)); }

    @Transactional
    public PagoResponseDto crear(PagoRequestDto dto) {
        Pago entity = new Pago();
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public PagoResponseDto actualizar(Long id, PagoRequestDto dto) {
        Pago entity = encontrar(id);
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) { encontrar(id).setActivo(activo); }

    @Transactional
    public void eliminar(Long id) { encontrar(id).setActivo(false); }

    private Pago encontrar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado")); }

    private void copiar(PagoRequestDto dto, Pago entity) {
        entity.setPedidoId(dto.pedidoId());
        entity.setMetodoPago(dto.metodoPago());
        entity.setMonto(dto.monto());
        entity.setEstado(dto.estado());
        entity.setCodigoTransaccion(dto.codigoTransaccion());


    }

    private PagoResponseDto toResponse(Pago e) {
        return new PagoResponseDto(e.getId(), e.getPedidoId(), e.getMetodoPago(), e.getMonto(), e.getEstado(), e.getCodigoTransaccion(), e.isActivo(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
