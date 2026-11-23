package com.sonidovivo.usuario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import com.sonidovivo.usuario.model.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByActivoTrue(Pageable pageable);
}
