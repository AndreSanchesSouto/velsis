package br.com.velsis.case_tecnico.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

        @NotBlank(message = "O campo 'role' precisa ser enviado")
        @Pattern(
                regexp = "^(USER|ADMIN)$",
                message = "A role deve ser USER ou ADMIN"
        )
        String role
) {}
