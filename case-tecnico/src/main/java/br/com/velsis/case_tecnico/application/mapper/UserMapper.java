package br.com.velsis.case_tecnico.application.mapper;

import br.com.velsis.case_tecnico.application.dto.response.GetUserResponseDTO;
import br.com.velsis.case_tecnico.application.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.domain.enums.Role;
import org.springframework.stereotype.Component;

/**
 * Componente responsável por realizar o mapeamento e conversão de dados entre a entidade
 * de domínio (UserEntity) e os objetos de transferência de dados (DTOs) de resposta de usuários.
 * Centraliza as regras de transformação para expor de forma segura apenas as informações necessárias
 * nas respostas das requirições HTTP, ocultando dados sensíveis como a senha criptografada.
 */
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
                user.getRole(),
                user.getLogin(),
                user.getCreatedAt()
        );
    }
}