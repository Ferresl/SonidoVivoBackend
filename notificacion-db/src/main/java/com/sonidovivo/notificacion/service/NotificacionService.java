package com.sonidovivo.notificacion.service;
import com.sonidovivo.notificacion.dto.*;
import com.sonidovivo.notificacion.model.*;
import com.sonidovivo.notificacion.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service @RequiredArgsConstructor
public class NotificacionService {
    private final NotificacionRepository repository;

    @Transactional(readOnly = true)
    public java.util.List<NotificacionResponseDto> listar() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public NotificacionResponseDto buscar(Long id) { return toResponse(encontrar(id)); }

    @Transactional
    public NotificacionResponseDto crear(NotificacionRequestDto dto) {
        Notificacion entity = new Notificacion();
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public NotificacionResponseDto actualizar(Long id, NotificacionRequestDto dto) {
        Notificacion entity = encontrar(id);
        copiar(dto, entity);
        return toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) { encontrar(id).setActivo(activo); }

    @Transactional
    public void eliminar(Long id) { encontrar(id).setActivo(false); }

    private Notificacion encontrar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrado")); }

    private void copiar(NotificacionRequestDto dto, Notificacion entity) {
        entity.setUsuarioId(dto.usuarioId());
        entity.setTitulo(dto.titulo());
        entity.setMensaje(dto.mensaje());
        entity.setTipo(dto.tipo());
        entity.setLeida(dto.leida());


    }

    private NotificacionResponseDto toResponse(Notificacion e) {
        return new NotificacionResponseDto(e.getId(), e.getUsuarioId(), e.getTitulo(), e.getMensaje(), e.getTipo(), e.getLeida(), e.isActivo(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
