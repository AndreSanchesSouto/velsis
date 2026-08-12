package br.com.velsis.case_tecnico.infrastructure.external.dto;

public record GetViaCepAddressResponseDTO(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
){ }
