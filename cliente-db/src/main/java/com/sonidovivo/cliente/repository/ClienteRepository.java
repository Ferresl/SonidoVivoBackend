package com.sonidovivo.cliente.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import com.sonidovivo.cliente.model.Cliente;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findByActivoTrue(Pageable pageable);
}
