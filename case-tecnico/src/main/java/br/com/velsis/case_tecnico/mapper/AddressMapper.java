package br.com.velsis.case_tecnico.mapper;

import br.com.velsis.case_tecnico.dto.response.PostAddressResponseDTO;
import br.com.velsis.case_tecnico.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.entity.AddressEntity;
import br.com.velsis.case_tecnico.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressMapper {
    public static PostAddressResponseDTO toResponse(AddressEntity address) {
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
}
