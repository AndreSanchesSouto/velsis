package br.com.velsis.case_tecnico.domain.service;

import br.com.velsis.case_tecnico.application.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.domain.exception.UserException;
import br.com.velsis.case_tecnico.application.factory.UserFactory;
import br.com.velsis.case_tecnico.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<UserEntity> findAll() {
        return this.repository.findAll();
    }

    public Optional<UserEntity> findById(UUID userId) {
        return this.repository.findById(userId);
    }

    public UserEntity getUserOrThrow(UUID userId) {
        return this.repository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado"));
    }

}
