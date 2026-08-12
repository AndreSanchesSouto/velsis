package br.com.velsis.case_tecnico.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostUserResponseDTO(
        UUID id,
        String name,
        LocalDateTime createdAt
) {}
