package br.com.velsis.case_tecnico.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatchUserRequestDTO(
        @Size(max = 100, min = 3, message = "O campo 'street' deve estar entre '3' e '100' caracteres")
        @Pattern(regexp = "^[\\p{L}0-9\\s.,'-]+$", message = "O campo 'street' contém caracteres inválidos")
        String name,

        @Size(max = 50, min = 3, message = "O campo 'login' deve estar entre '3' e '50' caracteres")
        @Pattern(
                regexp = "^[\\p{L}0-9_.-]+$",
                message = "O campo 'login' contém caracteres inválidos"
        )
        String login,

        @Pattern(
                regexp = "^(USER|ADMIN)$",
                message = "A role deve ser USER ou ADMIN"
        )
        String role,

        @Past(message = "A data de nascimento deve ser uma data passada")
        @JsonFormat(pattern = "dd/MM/yyyy") // Define o formato esperado na requisição (ex: 15/05/1990)
        LocalDate birthDate,

        @Pattern(
                regexp = "^\\d{11}$|^\\d{14}$",
                message = "O documento deve conter 11 dígitos (CPF) ou 14 dígitos (CNPJ), apenas números"
        )
        String document
) {}
