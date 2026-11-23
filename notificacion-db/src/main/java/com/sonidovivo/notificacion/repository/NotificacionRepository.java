package com.sonidovivo.notificacion.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import com.sonidovivo.notificacion.model.Notificacion;
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    Page<Notificacion> findByActivoTrue(Pageable pageable);
}
