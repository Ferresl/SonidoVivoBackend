package com.sonidovivo.cliente.service;
import com.sonidovivo.cliente.dto.*;
import com.sonidovivo.cliente.model.*;
import com.sonidovivo.cliente.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service @RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    @Transactional(readOnly = true)
    public java.util.List<ClienteResponseDto> listar() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public ClienteResponseDto buscar(Long id) { return toResponse(encontrar(id)); }

    @Transactional
    public ClienteResponseDto crear(ClienteRequestDto dto) {
        Cliente entity = new Cliente();
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public ClienteResponseDto actualizar(Long id, ClienteRequestDto dto) {
        Cliente entity = encontrar(id);
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) { encontrar(id).setActivo(activo); }

    @Transactional
    public void eliminar(Long id) { encontrar(id).setActivo(false); }

    private Cliente encontrar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado")); }

    private void copiar(ClienteRequestDto dto, Cliente entity) {
        entity.setNombre(dto.nombre());
        entity.setCorreo(dto.correo());
        entity.setTelefono(dto.telefono());
        entity.setDireccion(dto.direccion());


    }

    private ClienteResponseDto toResponse(Cliente e) {
        return new ClienteResponseDto(e.getId(), e.getNombre(), e.getCorreo(), e.getTelefono(), e.getDireccion(), e.isActivo(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
