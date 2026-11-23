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
    private final ProductoRepository repository;

    @Transactional(readOnly = true)
    public java.util.List<ProductoResponseDto> listar() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public ProductoResponseDto buscar(Long id) { return toResponse(encontrar(id)); }

    @Transactional
    public ProductoResponseDto crear(ProductoRequestDto solicitudProducto) {
        Producto productoNuevo = new Producto();
        copiar(solicitudProducto, productoNuevo);
        return toResponse(repository.saveAndFlush(productoNuevo));
    }

    @Transactional
    public ProductoResponseDto actualizar(Long id, ProductoRequestDto solicitudProducto) {
        Producto productoExistente = encontrar(id);
        copiar(solicitudProducto, productoExistente);
        return toResponse(repository.saveAndFlush(productoExistente));
    }

    @Transactional
    public void cambiarEstado(Long id, boolean activo) { encontrar(id).setActivo(activo); }

    @Transactional
    public void eliminar(Long id) { encontrar(id).setActivo(false); }

    private Producto encontrar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado")); }

    private void copiar(ProductoRequestDto solicitudProducto, Producto producto) {
        producto.setNombre(solicitudProducto.nombre());
        producto.setMarca(solicitudProducto.marca());
        producto.setDescripcion(solicitudProducto.descripcion());
        producto.setPrecio(solicitudProducto.precio());
        producto.setSku(solicitudProducto.sku());
        producto.setImagenUrl(solicitudProducto.imagenUrl());


    }

    private ProductoResponseDto toResponse(Producto producto) {
        return new ProductoResponseDto(producto.getId(), producto.getNombre(), producto.getMarca(), producto.getDescripcion(), producto.getPrecio(), producto.getSku(), producto.getImagenUrl(), producto.isActivo(), producto.getCreatedAt(), producto.getUpdatedAt());
    }
}
