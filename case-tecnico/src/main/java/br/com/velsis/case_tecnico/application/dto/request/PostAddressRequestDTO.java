package br.com.velsis.case_tecnico.application.dto.request;

import jakarta.validation.constraints.*;

public record PostAddressRequestDTO(
        @NotBlank(message = "O campo 'street' precisa ser informado")
        @Size(max = 100, min = 3, message = "O campo 'street' deve estar entre '3' e '100' caracteres")
        @Pattern(regexp = "^[\\p{L}0-9\\s.,'-]+$", message = "O campo 'street' contém caracteres inválidos")
        String street,

        @NotNull(message = "O campo 'number' precisa ser informado")
        @Positive(message = "O campo 'number' deve ser um número positivo")
        Integer number,

        @NotBlank(message = "O campo 'city' precisa ser informado")
        @Size(max = 100, min = 3, message = "O campo 'city' deve estar entre '3' e '100' caracteres")
        @Pattern(regexp = "^[\\p{L}\\s'-]+$", message = "O campo 'city' contém caracteres inválidos")
        String city,

        @NotBlank(message = "O campo 'uf' precisa ser informado")
        @Size(max = 2, min = 2, message = "O campo 'uf' deve ter exatamente '2' caracteres")
        @Pattern(
                regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
                message = "O campo 'uf' deve ser uma sigla de estado válida"
        )
        String uf,

        @NotBlank(message = "O campo 'zipcode' precisa ser informado")
        @Size(max = 15, min = 5, message = "O campo 'zipcode' deve estar entre '5' e '15' caracteres")
        @Pattern(
                regexp = "^\\d{5}-?\\d{3}$",
                message = "CEP inválido."
        )
        String zipcode
) {}
