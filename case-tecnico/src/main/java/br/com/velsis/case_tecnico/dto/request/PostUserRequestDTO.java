package br.com.velsis.case_tecnico.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PostUserRequestDTO(
        @NotBlank(message = "O campo 'name' precisa ser enviado")
        String name,

        @Valid
        PostAddressRequestDTO address
) {}
