package com.sonidovivo.pedido.service;
import com.sonidovivo.pedido.repository.*;
import com.sonidovivo.pedido.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
    @Mock PedidoRepository repositorio;
    @InjectMocks PedidoService servicio;
    @Test void buscarInexistenteDevuelve404() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());
        ResponseStatusException error = assertThrows(ResponseStatusException.class, () -> servicio.buscar(99L));
        assertEquals(404, error.getStatusCode().value());
    }
    @Test void eliminarDesactivaSinBorrarHistorial() {
        Pedido entidad = new Pedido();
        when(repositorio.findById(1L)).thenReturn(Optional.of(entidad));
        servicio.eliminar(1L);
        assertFalse(entidad.isActivo());
        verify(repositorio, never()).delete(any());
    }
    @Test void cambiarEstadoPermiteReactivar() {
        Pedido entidad = new Pedido();
        entidad.setActivo(false);
        when(repositorio.findById(1L)).thenReturn(Optional.of(entidad));
        servicio.cambiarEstado(1L, true);
        assertTrue(entidad.isActivo());
    }
}
