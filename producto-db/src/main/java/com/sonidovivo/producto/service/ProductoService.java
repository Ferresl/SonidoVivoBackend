package com.sonidovivo.producto.service;
import com.sonidovivo.producto.dto.*;
import com.sonidovivo.producto.model.*;
import com.sonidovivo.producto.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@Service @RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repositorio;
    @Transactional(readOnly = true)
    public java.util.List<ProductoResponseDto> listar() {
        return repositorio.findAll().stream().map(this::convertirRespuesta).toList();
    }
    @Transactional(readOnly = true)
    public ProductoResponseDto buscar(Long id) {
        return convertirRespuesta(encontrar(id));
    }
    @Transactional
    public ProductoResponseDto crear(ProductoRequestDto datos) {
        Producto entidad = new Producto();
        copiar(datos, entidad);
        return convertirRespuesta(repositorio.saveAndFlush(entidad));
    }
    @Transactional
    public ProductoResponseDto actualizar(Long id, ProductoRequestDto datos) {
        Producto entidad = encontrar(id);
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
    private Producto encontrar(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }
    private void copiar(ProductoRequestDto datos, Producto entidad) {
        entidad.setNombre(datos.nombre());
        entidad.setMarca(datos.marca());
        entidad.setDescripcion(datos.descripcion());
        entidad.setPrecio(datos.precio());
        entidad.setSku(datos.sku());
        entidad.setImagenUrl(datos.imagenUrl());
    }
    private ProductoResponseDto convertirRespuesta(Producto registro) {
        return new ProductoResponseDto(registro.getId(), registro.getNombre(), registro.getMarca(), registro.getDescripcion(), registro.getPrecio(), registro.getSku(), registro.getImagenUrl(), registro.isActivo(), registro.getCreatedAt(), registro.getUpdatedAt());
    }
}
