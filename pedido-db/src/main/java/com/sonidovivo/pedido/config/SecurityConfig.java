package com.sonidovivo.pedido.config;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
@Configuration
public class SecurityConfig {
    @Value("${app.jwt.secret}")
    private String claveSecreta;
    @Bean
    SecretKey claveJwt() {
        byte[] bytesClave = Base64.getDecoder().decode(claveSecreta);
        if (bytesClave.length < 32) {
            throw new IllegalArgumentException("JWT_SECRET debe contener al menos 32 bytes codificados en Base64");
        }
        return new SecretKeySpec(bytesClave, "HmacSHA256");
    }
    @Bean
    JwtDecoder decodificadorJwt(SecretKey clave) {
        return NimbusJwtDecoder.withSecretKey(clave).build();
    }
    @Bean
    JwtAuthenticationConverter convertidorAutenticacionJwt() {
        JwtAuthenticationConverter convertidor = new JwtAuthenticationConverter();
        convertidor.setJwtGrantedAuthoritiesConverter(tokenJwt -> {
            String rol = tokenJwt.getClaimAsString("rol");
            return rol == null ? List.of() : List.of(new SimpleGrantedAuthority("ROLE_" + rol));
        }
        );
        return convertidor;
    }
    @Bean
    SecurityFilterChain cadenaSeguridad(HttpSecurity seguridadHttp, JwtAuthenticationConverter convertidor) throws Exception {
        seguridadHttp.csrf(csrf -> csrf.disable())
        .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(permisos -> permisos
        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/health").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/productos/**", "/api/inventarios/**").permitAll()
        .requestMatchers("/api/**").hasRole("ADMIN")
        .anyRequest().authenticated())
        .oauth2ResourceServer(configuracionOauth -> configuracionOauth.jwt(tokenJwt -> tokenJwt.jwtAuthenticationConverter(convertidor)));
        return seguridadHttp.build();
    }
}
