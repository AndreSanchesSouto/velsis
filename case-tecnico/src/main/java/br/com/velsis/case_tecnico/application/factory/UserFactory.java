package br.com.velsis.case_tecnico.application.factory;

import br.com.velsis.case_tecnico.application.builder.UserBuilder;
import br.com.velsis.case_tecnico.application.dto.request.PatchAddressRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PatchUserRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;

public class UserFactory {
    public static UserEntity create(PostUserRequestDTO requestDTO) {
        UserEntity user = new UserBuilder()
                .name(requestDTO.name())
                .build();
        return user;
    }

    public static UserEntity update(UserEntity target, PatchUserRequestDTO requestDTO) {
        if (requestDTO.name() != null) target.setName(requestDTO.name());
        return target;
    }
}
