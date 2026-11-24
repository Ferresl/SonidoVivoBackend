package com.sonidovivo.producto.exception;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.*;
import org.springframework.dao.DataIntegrityViolationException;
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<ProblemDetail> dominio(ResponseStatusException e) {
         return ResponseEntity.status(e.getStatusCode()).body(ProblemDetail.forStatusAndDetail(e.getStatusCode(), e.getReason())); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ProblemDetail> validacion(MethodArgumentNotValidException e) { 
        return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getBindingResult().getFieldErrors().stream().map(f -> f.getField() + ": " + f.getDefaultMessage()).collect(java.util.stream.Collectors.joining("; ")))); }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ProblemDetail> conflicto() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                        "El SKU ya existe o los datos incumplen una restriccion de integridad")); }
}
