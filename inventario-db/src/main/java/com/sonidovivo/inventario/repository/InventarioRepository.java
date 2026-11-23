package com.sonidovivo.inventario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import com.sonidovivo.inventario.model.Inventario;
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Page<Inventario> findByActivoTrue(Pageable pageable);
}
