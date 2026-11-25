package com.sonidovivo.usuario.config;
import com.sonidovivo.usuario.model.Usuario;
import com.sonidovivo.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@RequiredArgsConstructor
public class AdminBootstrap {
    private final UsuarioRepository repositorio;
    @Bean
    CommandLineRunner crearAdministrador(
    @Value("${ADMIN_USERNAME:}") String nombreUsuario,
    @Value("${ADMIN_PASSWORD:}") String contrasena) {
        return argumentos -> {
            if (nombreUsuario.isBlank() || contrasena.isBlank() || repositorio.findByUsername(nombreUsuario).isPresent()) {
                return;
            }
            Usuario administrador = new Usuario();
            administrador.setClienteId(0L);
            administrador.setUsername(nombreUsuario);
            administrador.setPassword(new BCryptPasswordEncoder().encode(contrasena));
            administrador.setRol("ADMIN");
            repositorio.save(administrador);
        }
        ;
    }
}
