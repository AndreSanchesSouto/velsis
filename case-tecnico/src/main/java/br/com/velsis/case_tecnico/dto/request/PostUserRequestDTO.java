package br.com.velsis.case_tecnico.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostUserRequestDTO(
        @NotBlank
        String name
) {}
