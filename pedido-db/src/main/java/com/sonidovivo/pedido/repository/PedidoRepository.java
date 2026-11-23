package com.sonidovivo.pedido.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import com.sonidovivo.pedido.model.Pedido;
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<Pedido> findByActivoTrue(Pageable pageable);
}
