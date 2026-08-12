package br.com.velsis.case_tecnico.factory;

import br.com.velsis.case_tecnico.builder.AddressBuilder;
import br.com.velsis.case_tecnico.builder.UserBuilder;
import br.com.velsis.case_tecnico.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.entity.AddressEntity;
import br.com.velsis.case_tecnico.entity.UserEntity;
import org.springframework.stereotype.Component;

public class UserFactory {
    public static UserEntity create(PostUserRequestDTO requestDTO) {
        UserEntity user = new UserBuilder()
                .name(requestDTO.name())
                .build();

        if(requestDTO.address() != null) {
            PostAddressRequestDTO addressRequestDTO = requestDTO.address();
            AddressEntity address = new AddressBuilder()
                    .street(addressRequestDTO.street())
                    .number(addressRequestDTO.number())
                    .city(addressRequestDTO.city())
                    .uf(addressRequestDTO.uf())
                    .zipcode(addressRequestDTO.zipcode())
                    .build();
            user.addAddress(address);
        }

        return user;
    }
}
