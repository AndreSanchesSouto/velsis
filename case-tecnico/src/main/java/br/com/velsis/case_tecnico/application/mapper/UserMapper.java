package br.com.velsis.case_tecnico.application.mapper;

import br.com.velsis.case_tecnico.application.dto.response.GetUserResponseDTO;
import br.com.velsis.case_tecnico.application.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static PostUserResponseDTO toPostResponse(UserEntity user) {
        return new PostUserResponseDTO(
                user.getId(),
                user.getName(),
                user.getCreatedAt()
        );
    }

    public static GetUserResponseDTO toGetResponse(UserEntity user) {
        return new GetUserResponseDTO(
                user.getId(),
                user.getName(),
                user.getCreatedAt()
        );
    }
}
