package com.sonidovivo.notificacion.controller;
import com.sonidovivo.notificacion.service.NotificacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class NotificacionControllerTest {
    @Mock NotificacionService servicio;
    @InjectMocks NotificacionController controller;
    @Test void eliminarDevuelve204YDelegaEnServicio() {
        assertEquals(204, controller.eliminar(1L).getStatusCode().value());
        verify(servicio).eliminar(1L);
    }
    @Test void cambiarEstadoDevuelve204YConservaParametro() {
        assertEquals(204, controller.cambiarEstado(2L, false).getStatusCode().value());
        verify(servicio).cambiarEstado(2L, false);
    }
}
