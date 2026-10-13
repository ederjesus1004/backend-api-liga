package dev.eder.liga.exception;

public class OperacionNoPermitidaException extends RuntimeException{
    public OperacionNoPermitidaException(String mensaje){
        super(mensaje);
    }
}
