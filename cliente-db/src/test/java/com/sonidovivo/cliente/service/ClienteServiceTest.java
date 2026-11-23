package com.sonidovivo.cliente.service;
import com.sonidovivo.cliente.repository.*;
import com.sonidovivo.cliente.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    @Mock ClienteRepository repository;
    @InjectMocks ClienteService service;

    @Test void buscarInexistenteDevuelve404() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> service.buscar(99L));
        assertEquals(404, e.getStatusCode().value());
    }

    @Test void eliminarDesactivaSinBorrarHistorial() {
        Cliente entity = new Cliente();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.eliminar(1L);
        assertFalse(entity.isActivo());
        verify(repository, never()).delete(any());
    }

    @Test void cambiarEstadoPermiteReactivar() {
        Cliente entity = new Cliente(); entity.setActivo(false);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.cambiarEstado(1L, true);
        assertTrue(entity.isActivo());
    }
}
