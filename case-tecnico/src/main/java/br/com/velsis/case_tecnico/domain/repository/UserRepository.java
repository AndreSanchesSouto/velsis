package br.com.velsis.case_tecnico.domain.repository;

import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository // Define a interface como um repositório de dados gerenciado pelo Spring Data JPA
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByLogin(String login);

    /**
     * Consulta customizada (JPQL) com paginação para buscar usuários ativos.
     * Filtra registros onde 'deletedAt' é nulo e realiza uma busca parcial (case-insensitive) pelo nome ou login,
     * ignorando o filtro caso o termo enviado esteja vazio.
     */
    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE u.deletedAt IS NULL
            AND (
                :search = '' OR
                    LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                    LOWER(u.login) LIKE LOWER(CONCAT('%', :search, '%'))
            )
    """)
    Page<UserEntity> findAllActive(@Param("search") String search, Pageable pageable);

    @Modifying
    @Query("""
        UPDATE AddressEntity a
            SET a.deletedAt = CURRENT_TIMESTAMP
        WHERE a.user.id = :id
          AND a.deletedAt IS NULL
    """)
    void diableById(@Param("id") UUID id);

    @Query("""
    SELECT COUNT(u)
    FROM UserEntity u
    WHERE u.deletedAt IS NULL
    """)
    long getActivesCount();
}
