package br.com.velsis.case_tecnico.domain.repository;

import br.com.velsis.case_tecnico.domain.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository // Define a interface como um repositório de dados gerenciado pelo Spring Data JPA
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {

    List<AddressEntity> findByUserIdAndDeletedAtIsNull(UUID id);
}
