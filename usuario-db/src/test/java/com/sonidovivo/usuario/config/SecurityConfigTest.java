package com.sonidovivo.usuario.config;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    @Test
    void firmaYVerificaJwt() {
        SecurityConfig config = new SecurityConfig();
        ReflectionTestUtils.setField(config, "claveSecreta", "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=");
        var key = config.claveJwt();
        var claims = JwtClaimsSet.builder()
            .subject("admin")
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(60))
            .claim("rol", "ADMIN")
            .build();
        var header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = config.codificadorJwt(key).encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        var decoded = config.decodificadorJwt(key).decode(token);
        assertEquals("admin", decoded.getSubject());
        assertEquals("ADMIN", decoded.getClaimAsString("rol"));
    }
}
