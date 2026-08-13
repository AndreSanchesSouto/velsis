package br.com.velsis.case_tecnico.domain.service;

import br.com.velsis.case_tecnico.application.dto.request.PatchUserRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.application.factory.AddressFactory;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.domain.exception.UserException;
import br.com.velsis.case_tecnico.application.factory.UserFactory;
import br.com.velsis.case_tecnico.domain.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity post(PostUserRequestDTO requestDTO) {
        final UserEntity user = UserFactory.create(requestDTO);
        return this.repository.save(user);
    }

    public UserEntity register(UserEntity userEntity) {
        return this.repository.save(userEntity);
    }

    public List<UserEntity> findAll() {
        return this.repository.findAll();
    }

    public UserEntity findById(UUID userId) {
        return this.getUserOrThrow(userId);
    }

    public UserEntity getUserOrThrow(UUID userId) {
        return this.repository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado"));
    }

    public UserEntity update(UUID id, PatchUserRequestDTO requestDTO) {
        final UserEntity target = this.getUserOrThrow(id);
        final UserEntity updated = UserFactory.update(target, requestDTO);
        return repository.save(updated);
    }
}
