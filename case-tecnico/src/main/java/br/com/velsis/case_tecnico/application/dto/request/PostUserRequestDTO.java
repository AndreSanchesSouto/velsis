package br.com.velsis.case_tecnico.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostUserRequestDTO(
        @NotBlank(message = "O campo 'name' precisa ser enviado")
        @Size(max = 100, min = 3, message = "O campo 'name' deve estar entre '3' e '100' caracteres")
        @Pattern(regexp = "^[\\p{L}0-9\\s.,'-]+$", message = "O campo 'name' contém caracteres inválidos")
        String name,

        @NotBlank(message = "O campo 'login' precisa ser enviado")
        @Size(max = 50, min = 3, message = "O campo 'login' deve estar entre '3' e '50' caracteres")
        @Pattern(
                regexp = "^[\\p{L}0-9_.-]+$",
                message = "O campo 'login' contém caracteres inválidos"
        )
        String login,

        @NotBlank(message = "O campo 'password' precisa ser enviado")
        @Size(max = 100, min = 8, message = "O campo 'password' deve estar entre '8' e '50' caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])\\S+$",
                message = "A senha deve conter letras, números e caracteres especiais e não pode conter espaços"
        )
        String password,

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
