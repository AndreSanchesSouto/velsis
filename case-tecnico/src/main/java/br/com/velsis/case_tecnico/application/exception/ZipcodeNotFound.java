package br.com.velsis.case_tecnico.application.exception;

public class ZipcodeNotFound extends RuntimeException {
    public ZipcodeNotFound(String message) {
        super(message);
    }
}
