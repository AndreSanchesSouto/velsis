package br.com.velsis.case_tecnico.application.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
        String role
) {}
