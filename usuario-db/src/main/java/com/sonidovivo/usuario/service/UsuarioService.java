package com.sonidovivo.usuario.service;
import com.sonidovivo.usuario.dto.*;
import com.sonidovivo.usuario.model.*;
import com.sonidovivo.usuario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service @RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repositorio;
    @Transactional(readOnly = true)
    public java.util.List<UsuarioResponseDto> listar() {
        return repositorio.findAll().stream().map(this::convertirRespuesta).toList();
    }
    @Transactional(readOnly = true)
    public UsuarioResponseDto buscar(Long id) {
        return convertirRespuesta(encontrar(id));
    }
    @Transactional
    public UsuarioResponseDto crear(UsuarioRequestDto datos) {
        Usuario entidad = new Usuario();
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public UsuarioResponseDto actualizar(Long id, UsuarioRequestDto datos) {
        Usuario entidad = encontrar(id);
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
    private Usuario encontrar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }
    private void copiar(UsuarioRequestDto datos, Usuario entidad) {
        entidad.setClienteId(datos.clienteId());
        entidad.setUsername(datos.nombreUsuario());
        entidad.setPassword(new BCryptPasswordEncoder().encode(datos.contrasena()));
        if (entidad.getRol() == null) {
            entidad.setRol("CLIENTE");
        }
    }
    private UsuarioResponseDto convertirRespuesta(Usuario registro) {
        return new UsuarioResponseDto(registro.getId(), registro.getClienteId(), registro.getUsername(), registro.getRol(), registro.isActivo(), registro.getCreatedAt(), registro.getUpdatedAt());
    }
}
