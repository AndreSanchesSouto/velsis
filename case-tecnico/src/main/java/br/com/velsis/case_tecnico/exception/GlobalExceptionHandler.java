package br.com.velsis.case_tecnico.exception;

import br.com.velsis.case_tecnico.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception
    ) {
        ErrorResponseDTO response = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Corpo da requisição ausente ou inválido"
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
