package br.com.velsis.case_tecnico.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PostAuthenticationRequestDTO(
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

        @Size(max = 100, min = 8, message = "O campo 'confirmPassword' deve estar entre '8' e '50' caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])\\S+$",
                message = "A senha deve conter letras, números e caracteres especiais e não pode conter espaços"
        )
        String confirmPassword

) {}
