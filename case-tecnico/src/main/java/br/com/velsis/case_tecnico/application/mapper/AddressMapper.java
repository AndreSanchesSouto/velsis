package br.com.velsis.case_tecnico.application.mapper;

import br.com.velsis.case_tecnico.application.dto.response.GetAddressResponseDTO;
import br.com.velsis.case_tecnico.application.dto.response.PostAddressResponseDTO;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;
import org.springframework.stereotype.Component;

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
