package br.com.velsis.case_tecnico.domain.exception;

public class CredentialsMismatchException extends RuntimeException {
    public CredentialsMismatchException(String message) {
        super(message);
    }
}
