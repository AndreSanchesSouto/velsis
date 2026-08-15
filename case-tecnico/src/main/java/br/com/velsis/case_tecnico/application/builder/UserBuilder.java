package br.com.velsis.case_tecnico.application.builder;

import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.domain.enums.Role;

import java.time.LocalDateTime;

/**
 * Classe utilitária que implementa o padrão de projeto Builder para a criação fluente
 * e encapsulada de instâncias da entidade de domínio UserEntity.
 * Facilita a construção progressiva de objetos de usuário através do encadeamento de métodos,
 * permitindo a definição clara de credenciais, perfis de acesso (Role) e metadados de ciclo de vida
 * antes da instanciação final e do armazenamento no banco de dados.
 */
public class UserBuilder {

    private String name;
    private String password;
    private String login;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserBuilder deletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder login(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder role(Role role) {
        this.role = role;
        return this;
    }

    public UserEntity build() {
        UserEntity user = new UserEntity();

        user.setName(name);
        user.setCreatedAt(createdAt);
        user.setDeletedAt(deletedAt);
        user.setPassword(password);
        user.setLogin(login);
        user.setRole(role);

        return user;
    }
}
