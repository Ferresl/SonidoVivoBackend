package com.sonidovivo.carrito.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import com.sonidovivo.carrito.model.Carrito;
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Page<Carrito> findByActivoTrue(Pageable pageable);
}
