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
    private final NotificacionRepository repositorio;
    @Transactional(readOnly = true)
    public java.util.List<NotificacionResponseDto> listar() {
        return repositorio.findAll().stream().map(this::convertirRespuesta).toList();
    }
    @Transactional(readOnly = true)
    public NotificacionResponseDto buscar(Long id) {
        return convertirRespuesta(encontrar(id));
    }
    @Transactional
    public NotificacionResponseDto crear(NotificacionRequestDto datos) {
        Notificacion entidad = new Notificacion();
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public NotificacionResponseDto actualizar(Long id, NotificacionRequestDto datos) {
        Notificacion entidad = encontrar(id);
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
    private Notificacion encontrar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrado"));
    }
    private void copiar(NotificacionRequestDto datos, Notificacion entidad) {
        entidad.setUsuarioId(datos.usuarioId());
        entidad.setTitulo(datos.titulo());
        entidad.setMensaje(datos.mensaje());
        entidad.setTipo(datos.tipo());
        entidad.setLeida(datos.leida());
    }
    private NotificacionResponseDto convertirRespuesta(Notificacion registro) {
        return new NotificacionResponseDto(registro.getId(), registro.getUsuarioId(), registro.getTitulo(), registro.getMensaje(), registro.getTipo(), registro.getLeida(), registro.isActivo(), registro.getCreatedAt(), registro.getUpdatedAt());
    }
}
