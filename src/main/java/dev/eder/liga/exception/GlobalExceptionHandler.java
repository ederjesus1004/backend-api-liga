package dev.eder.liga.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> armarRespuesta(HttpStatus status, String mensaje, Map<String, String> errores){
        ErrorResponse body = new ErrorResponse(LocalDateTime.now(), status.value(), mensaje, errores);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejaNoEncontrado(RecursoNoEncontradoException ex){
        return armarRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of());
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> manejarDuplicados(RecursoDuplicadoException ex){
        return armarRespuesta(HttpStatus.CONFLICT,ex.getMessage(), Map.of());
    }

    

    // NUEVO: también es 409 (conflicto con el estado actual de los datos)
    @ExceptionHandler(OperacionNoPermitidaException.class)
    public ResponseEntity<ErrorResponse> manejarNoPermita(OperacionNoPermitidaException ex){
        return armarRespuesta(HttpStatus.CONFLICT, ex.getMessage(), Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex){
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
        .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return armarRespuesta(HttpStatus.BAD_REQUEST, "Datos invalidos", errores);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex){
        return  armarRespuesta(HttpStatus.BAD_REQUEST,"El JSON enviado no es valido", Map.of());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(Exception ex){
        log.error("Error inseperado", ex);
        return armarRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inesperado ", Map.of());
    }
}
