package br.com.velsis.case_tecnico.application.mapper;

import br.com.velsis.case_tecnico.application.dto.response.GetAddressResponseDTO;
import br.com.velsis.case_tecnico.application.dto.response.PostAddressResponseDTO;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;
import org.springframework.stereotype.Component;

/**
 * Componente responsável por realizar o mapeamento e conversão de dados entre a entidade
 * de domínio (AddressEntity) e os objetos de transferência de dados (DTOs) de resposta.
 * Centraliza as regras de transformação para expor de forma segura apenas as informações
 * necessárias nas respostas das requisições HTTP (criação e consulta de endereços).
 */
@Component
public class AddressMapper {
    public static PostAddressResponseDTO toPostResponse(AddressEntity address) {
        return new PostAddressResponseDTO(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getCity(),
                address.getUf(),
                address.getZipcode(),
                address.getCreatedAt()
        );
    }

    public static GetAddressResponseDTO toGetResponse(AddressEntity address) {
        return new GetAddressResponseDTO(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getCity(),
                address.getUf(),
                address.getZipcode(),
                address.getCreatedAt(),
                address.getUser().getId()
        );
    }
}
