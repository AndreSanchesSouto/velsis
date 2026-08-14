package br.com.velsis.case_tecnico.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ValidationErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String message,
        Map<String, List<String>> errors
) { }
