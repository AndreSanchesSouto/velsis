package br.com.velsis.case_tecnico.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PostRegisterRequestDTO(
        @NotNull(message = "Os dados de autenticação precisam ser enviados")
        @Valid
        PostAuthenticationRequestDTO authentication,

        @NotNull(message = "Os dados de autenticação precisam ser enviados")
        @Valid
        PostUserRequestDTO user
){ }
