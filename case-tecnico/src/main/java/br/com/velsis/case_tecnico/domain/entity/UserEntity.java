package br.com.velsis.case_tecnico.domain.entity;

import br.com.velsis.case_tecnico.domain.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidade de domínio que representa a tabela de usuários ("users") no banco de dados.
 * Mapeia as credenciais de autenticação (login, senha criptografada), nome e perfil de acesso (Role).
 * Estabelece um relacionamento de um para muitos (OneToMany) com a entidade de endereços (AddressEntity),
 * gerenciando o ciclo de vida dos endereços de forma cascateada (CascadeType.ALL e orphanRemoval).
 * Possui suporte para auditoria de criação automática, controle de exclusão lógica (soft delete) pelo campo deletedAt
 * e metodo utilitário para checagem de permissões administrativas.
 */
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "document", unique = true)
    private String document;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses = new ArrayList<>();

    public UserEntity() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    private void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(AddressEntity address) {
        if (address != null) {
            this.addresses.add(address);
            address.setUser(this);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }
}
