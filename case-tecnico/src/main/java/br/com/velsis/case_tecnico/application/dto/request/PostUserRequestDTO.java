package br.com.velsis.case_tecnico.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PostUserRequestDTO(
        @NotBlank(message = "O campo 'name' precisa ser enviado")
        @Size(max = 100, min = 3, message = "O campo 'name' deve estar entre '3' e '100' caracteres")
        @Pattern(regexp = "^[\\p{L}0-9\\s.,'-]+$", message = "O campo 'name' contém caracteres inválidos")
        String name
) {}
