package br.com.velsis.case_tecnico.dto.response;

import br.com.velsis.case_tecnico.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetAddressResponseDTO(
        UUID id,
        String street,
        Integer number,
        String city,
        String uf,
        String zipcode,
        LocalDateTime createdAt,
        UUID userId
) { }
