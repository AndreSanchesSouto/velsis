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

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByLogin(String login);

    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE u.deletedAt IS NULL
    """)
    Page<UserEntity> findAllActive(Pageable pageable);

    @Modifying
    @Query("""
        UPDATE AddressEntity a
            SET a.deletedAt = CURRENT_TIMESTAMP
        WHERE a.user.id = :id
          AND a.deletedAt IS NULL
    """)
    void diableById(@Param("id") UUID id);

}
