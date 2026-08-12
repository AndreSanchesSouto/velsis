package br.com.velsis.case_tecnico.application.builder;

import br.com.velsis.case_tecnico.domain.entity.UserEntity;

import java.time.LocalDateTime;

public class UserBuilder {

    private String name;
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

    public UserEntity build() {
        UserEntity user = new UserEntity();

        user.setName(name);
        user.setCreatedAt(createdAt);
        user.setDeletedAt(deletedAt);

        return user;
    }
}
