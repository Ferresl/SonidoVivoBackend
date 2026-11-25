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
    private final ClienteRepository repositorio;
    @Transactional(readOnly = true)
    public java.util.List<ClienteResponseDto> listar() {
        return repositorio.findAll().stream().map(this::convertirRespuesta).toList();
    }
    @Transactional(readOnly = true)
    public ClienteResponseDto buscar(Long id) {
        return convertirRespuesta(encontrar(id));
    }
    @Transactional
    public ClienteResponseDto crear(ClienteRequestDto datos) {
        Cliente entidad = new Cliente();
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public ClienteResponseDto actualizar(Long id, ClienteRequestDto datos) {
        Cliente entidad = encontrar(id);
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
    private Cliente encontrar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }
    private void copiar(ClienteRequestDto datos, Cliente entidad) {
        entidad.setNombre(datos.nombre());
        entidad.setCorreo(datos.correo());
        entidad.setTelefono(datos.telefono());
        entidad.setDireccion(datos.direccion());
    }
    private ClienteResponseDto convertirRespuesta(Cliente registro) {
        return new ClienteResponseDto(registro.getId(), registro.getNombre(), registro.getCorreo(), registro.getTelefono(), registro.getDireccion(), registro.isActivo(), registro.getCreatedAt(), registro.getUpdatedAt());
    }
}
