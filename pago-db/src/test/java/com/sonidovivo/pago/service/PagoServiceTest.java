package com.sonidovivo.pago.service;
import com.sonidovivo.pago.repository.*;
import com.sonidovivo.pago.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
class PagoServiceTest {
    @Mock PagoRepository repositorio;
    @InjectMocks PagoService servicio;
    @Test void buscarInexistenteDevuelve404() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());
        ResponseStatusException error = assertThrows(ResponseStatusException.class, () -> servicio.buscar(99L));
        assertEquals(404, error.getStatusCode().value());
    }
    @Test void eliminarDesactivaSinBorrarHistorial() {
        Pago entidad = new Pago();
        when(repositorio.findById(1L)).thenReturn(Optional.of(entidad));
        servicio.eliminar(1L);
        assertFalse(entidad.isActivo());
        verify(repositorio, never()).delete(any());
    }
    @Test void cambiarEstadoPermiteReactivar() {
        Pago entidad = new Pago();
        entidad.setActivo(false);
        when(repositorio.findById(1L)).thenReturn(Optional.of(entidad));
        servicio.cambiarEstado(1L, true);
        assertTrue(entidad.isActivo());
    }
}
