package br.com.velsis.case_tecnico.application.factory;

import br.com.velsis.case_tecnico.application.builder.UserBuilder;
import br.com.velsis.case_tecnico.application.dto.request.PatchUserRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostRegisterRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.domain.enums.Role;

/**
 * Classe de fábrica (Factory) responsável por centralizar a lógica de criação, registro
 * e atualização de instâncias da entidade de domínio UserEntity.
 * Utiliza o padrão Builder para a construção isolada de objetos de usuários e incorpora
 * um validador interno para mapear strings recebidas nas requisições HTTP para o Enum Role.
 * Também suporta atualizações parciais de dados e fluxos específicos de auto-cadastro (register).
 */
public class UserFactory {

    private static Role roleValidator(String roleString) {
        final Role role;
        try {
            role = Role.valueOf(roleString.toUpperCase()
            );
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Role inválida: " + roleString
            );
        }
        return role;
    }

    public static UserEntity create(PostUserRequestDTO requestDTO) {
        final Role role = roleValidator(requestDTO.role());

        return new UserBuilder()
                .name(requestDTO.name())
                .login(requestDTO.login())
                .role(role)
                .build();
    }

    public static UserEntity update(UserEntity target, PatchUserRequestDTO requestDTO) {
        final Role role = roleValidator(requestDTO.role());

        if (requestDTO.name() != null) target.setName(requestDTO.name());
        if (requestDTO.role() != null) target.setRole(role);
        if (requestDTO.login() != null) target.setLogin(requestDTO.login());
        return target;
    }

    public static UserEntity register(PostRegisterRequestDTO requestDTO) {
        UserEntity user = new UserBuilder()
                .name(requestDTO.name())
                .password(requestDTO.authentication().password())
                .login(requestDTO.authentication().login())
                .build();
        return user;
    }
}
