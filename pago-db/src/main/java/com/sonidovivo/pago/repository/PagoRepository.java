package com.sonidovivo.pago.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import com.sonidovivo.pago.model.Pago;
public interface PagoRepository extends JpaRepository<Pago, Long> {
    Page<Pago> findByActivoTrue(Pageable pageable);
}
