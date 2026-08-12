package br.com.velsis.case_tecnico.application.factory;

import br.com.velsis.case_tecnico.application.builder.UserBuilder;
import br.com.velsis.case_tecnico.application.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;

public class UserFactory {
    public static UserEntity create(PostUserRequestDTO requestDTO) {
        UserEntity user = new UserBuilder()
                .name(requestDTO.name())
                .build();

        if(requestDTO.address() != null) {
            AddressEntity address = AddressFactory.create(requestDTO.address());
            user.addAddress(address);
        }

        return user;
    }
}
