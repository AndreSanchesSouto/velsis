package br.com.velsis.case_tecnico.application.dto.response;

import br.com.velsis.case_tecnico.domain.enums.Role;

import java.util.UUID;

public record LoginResponseDTO(
        String token,
        UUID id,
        Role role
) { }
