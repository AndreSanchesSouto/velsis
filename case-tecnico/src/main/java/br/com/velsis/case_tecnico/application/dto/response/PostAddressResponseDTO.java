package br.com.velsis.case_tecnico.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostAddressResponseDTO(
        UUID id,
        String street,
        Integer number,
        String city,
        String uf,
        String zipcode,
        LocalDateTime createdAt
) {}
