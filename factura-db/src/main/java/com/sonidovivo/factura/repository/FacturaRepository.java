package com.sonidovivo.factura.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import com.sonidovivo.factura.model.Factura;
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Page<Factura> findByActivoTrue(Pageable pageable);
}
