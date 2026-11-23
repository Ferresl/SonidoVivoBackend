package com.sonidovivo.producto.controller;
import com.sonidovivo.producto.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {
    @Mock ProductoService service;
    @InjectMocks ProductoController controller;

    @Test void eliminarDevuelve204YDelegaEnServicio() {
        assertEquals(204, controller.eliminar(1L).getStatusCode().value());
        verify(service).eliminar(1L);
    }

    @Test void cambiarEstadoDevuelve204YConservaParametro() {
        assertEquals(204, controller.cambiarEstado(2L, false).getStatusCode().value());
        verify(service).cambiarEstado(2L, false);
    }
}

