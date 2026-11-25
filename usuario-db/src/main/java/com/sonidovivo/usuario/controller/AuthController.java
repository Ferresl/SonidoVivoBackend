package com.sonidovivo.usuario.controller;
import com.sonidovivo.usuario.repository.UsuarioRepository;
import com.sonidovivo.usuario.dto.UsuarioRequestDto;
import com.sonidovivo.usuario.dto.UsuarioResponseDto;
import com.sonidovivo.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository repositorio;
    private final UsuarioService servicio;
    private final JwtEncoder codificador;
    private final BCryptPasswordEncoder codificadorClave = new BCryptPasswordEncoder();
    public record LoginRequest(@NotBlank String nombreUsuario, @NotBlank String contrasena) {
    }
    public record TokenResponse(String token, String tipo, long expiraEnSegundos) {
    }
    @PostMapping("/api/usuarios/registro")
    public ResponseEntity<UsuarioResponseDto> registrarUsuario(@Valid @RequestBody UsuarioRequestDto datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(datos));
    }
    @PostMapping("/api/auth/login")
    public ResponseEntity<TokenResponse> iniciarSesion(@Valid @RequestBody LoginRequest datos) {
        var usuario = repositorio.findByUsername(datos.nombreUsuario())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));
        if (!usuario.isActivo() || !codificadorClave.matches(datos.contrasena(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }
        return ResponseEntity.ok(emitir(usuario.getUsername(), usuario.getRol()));
    }
    @PostMapping("/api/auth/renovar")
    public ResponseEntity<TokenResponse> renovar(org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken autenticacion) {
        var usuario = repositorio.findByUsername(autenticacion.getName())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario inexistente"));
        if (!usuario.isActivo()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario inactivo");
        }
        return ResponseEntity.ok(emitir(usuario.getUsername(), usuario.getRol()));
    }
    private TokenResponse emitir(String nombreUsuario, String rol) {
        Instant ahora = Instant.now();
        JwtClaimsSet datosToken = JwtClaimsSet.builder()
        .issuer("sonidovivo")
        .subject(nombreUsuario)
        .issuedAt(ahora)
        .expiresAt(ahora.plusSeconds(3600))
        .claim("rol", rol)
        .build();
        JwsHeader cabecera = JwsHeader.with(MacAlgorithm.HS256).build();
        return new TokenResponse(codificador.encode(JwtEncoderParameters.from(cabecera, datosToken)).getTokenValue(), "Bearer", 3600);
    }
}
