package br.com.velsis.case_tecnico.mapper;

import br.com.velsis.case_tecnico.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static PostUserResponseDTO toResponse(UserEntity user) {
        return new PostUserResponseDTO(
                user.getId(),
                user.getName(),
                user.getCreatedAt()
        );
    }
}
