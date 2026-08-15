package br.com.velsis.case_tecnico.application.dto.response;

import br.com.velsis.case_tecnico.domain.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetUserResponseDTO(
    UUID id,
    String name,
    Role role,
    String login,
    String document,
    LocalDate birthDate,
    LocalDateTime createdAt
) { }
