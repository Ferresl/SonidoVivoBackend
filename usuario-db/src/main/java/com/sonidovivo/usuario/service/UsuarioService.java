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
    private final UsuarioRepository repository;

    @Transactional(readOnly = true)
    public java.util.List<UsuarioResponseDto> listar() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public UsuarioResponseDto buscar(Long id) { return toResponse(encontrar(id)); }

    @Transactional
    public UsuarioResponseDto crear(UsuarioRequestDto solicitudUsuario) {
        Usuario usuarioNuevo = new Usuario();
        copiar(solicitudUsuario, usuarioNuevo);
        return toResponse(repository.saveAndFlush(usuarioNuevo));
    }

    @Transactional
    public UsuarioResponseDto actualizar(Long id, UsuarioRequestDto solicitudUsuario) {
        Usuario usuarioExistente = encontrar(id);
        copiar(solicitudUsuario, usuarioExistente);
        return toResponse(repository.saveAndFlush(usuarioExistente));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) { encontrar(id).setActivo(activo); }

    @Transactional
    public void eliminar(Long id) { encontrar(id).setActivo(false); }

    private Usuario encontrar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")); }

    private void copiar(UsuarioRequestDto solicitudUsuario, Usuario usuario) {
        usuario.setClienteId(solicitudUsuario.clienteId());
        usuario.setUsername(solicitudUsuario.username());
        usuario.setPassword(new BCryptPasswordEncoder().encode(solicitudUsuario.password()));
        usuario.setRol(solicitudUsuario.rol());


    }

    private UsuarioResponseDto toResponse(Usuario usuario) {
        return new UsuarioResponseDto(usuario.getId(), usuario.getClienteId(), usuario.getUsername(), usuario.getRol(), usuario.isActivo(), usuario.getCreatedAt(), usuario.getUpdatedAt());
    }
}
