package br.com.velsis.case_tecnico.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostAddressRequestDTO(
        @NotBlank(message = "O campo 'street' precisa ser informado")
        String street,

        @NotBlank(message = "O campo 'number' precisa ser informado")
        Integer number,

        @NotBlank(message = "O campo 'city' precisa ser informado")
        String city,

        @NotBlank(message = "O campo 'uf' precisa ser informado")
        String uf,

        @NotBlank(message = "O campo 'zipcode' precisa ser informado")
        String zipcode
) {}
