package br.com.velsis.case_tecnico.factory;

import br.com.velsis.case_tecnico.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public static UserEntity create(PostUserRequestDTO requestDTO) {
        UserEntity user = new UserEntity();
        user.setName(requestDTO.name());
        return user;
    }
}
