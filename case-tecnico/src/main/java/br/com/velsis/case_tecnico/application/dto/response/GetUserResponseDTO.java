package br.com.velsis.case_tecnico.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetUserResponseDTO(
    UUID id,
    String name,
    LocalDateTime createdAt
) { }
