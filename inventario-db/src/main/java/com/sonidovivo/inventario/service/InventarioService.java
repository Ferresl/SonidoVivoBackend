package com.sonidovivo.inventario.service;
import com.sonidovivo.inventario.dto.*;
import com.sonidovivo.inventario.model.*;
import com.sonidovivo.inventario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@Service @RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository repositorio;
    @Transactional(readOnly = true)
    public java.util.List<InventarioResponseDto> listar() {
        return repositorio.findAll().stream().map(this::convertirRespuesta).toList();
    }
    @Transactional(readOnly = true)
    public InventarioResponseDto buscar(Long id) {
        return convertirRespuesta(encontrar(id));
    }
    @Transactional
    public InventarioResponseDto crear(InventarioRequestDto datos) {
        Inventario entidad = new Inventario();
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public InventarioResponseDto actualizar(Long id, InventarioRequestDto datos) {
        Inventario entidad = encontrar(id);
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
    private Inventario encontrar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario no encontrado"));
    }
    private void copiar(InventarioRequestDto datos, Inventario entidad) {
        entidad.setProductoId(datos.productoId());
        entidad.setStockActual(datos.stockActual());
        entidad.setStockReservado(datos.stockReservado());
        entidad.setStockMinimo(datos.stockMinimo());
        if (datos.stockReservado() > datos.stockActual()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock reservado supera stock actual");
    }
    private InventarioResponseDto convertirRespuesta(Inventario registro) {
        return new InventarioResponseDto(registro.getId(), registro.getProductoId(), registro.getStockActual(), registro.getStockReservado(), registro.getStockMinimo(), registro.isActivo(), registro.getCreatedAt(), registro.getUpdatedAt());
    }
}
