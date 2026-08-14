package br.com.velsis.case_tecnico.application.service;

import br.com.velsis.case_tecnico.application.dto.request.PatchUserRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.domain.exception.UserException;
import br.com.velsis.case_tecnico.application.factory.UserFactory;
import br.com.velsis.case_tecnico.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity post(PostUserRequestDTO requestDTO) {
        final UserEntity user = UserFactory.create(requestDTO);
        final String loginAsPassword = this.generateLogin(requestDTO.name());
        user.setPassword(passwordEncoder.encode(loginAsPassword));

        return this.register(user);
    }

    public String generateLogin(String name) {
        String normalizedName = Normalizer
                .normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim();

        String[] parts = normalizedName.split("\\s+");

        String firstName = parts[0];
        String lastName = parts[parts.length - 1];

        return firstName + "." + lastName + UUID.randomUUID().toString().substring(0, 8);
    }

    public UserEntity register(UserEntity userEntity) {
        if (this.repository.findByLogin(userEntity.getLogin()).isPresent()) {
            throw new UserException("Usuário já registrado");
        }
        return this.repository.save(userEntity);
    }

    public Page<UserEntity> findAllActives(Pageable pageable) {
        return this.repository.findAllActive(pageable);
    }

    public UserEntity findById(UUID userId) {
        return this.getUserOrThrow(userId);
    }

    public UserEntity getUserOrThrow(UUID userId) {
        return this.repository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado"));
    }

    public Optional<UserEntity> findByLogin(String login) {
        return this.findByLogin(login);
    }

    public UserEntity update(UUID id, PatchUserRequestDTO requestDTO) {
        final UserEntity target = this.getUserOrThrow(id);
            this.repository.findByLogin(requestDTO.login())
                    .ifPresent(user -> {
                        if(!user.getId().equals(id)) {
                            throw new UserException("Login já registrado");
                        }
                    }
            );

        final UserEntity updated = UserFactory.update(target, requestDTO);
        return repository.save(updated);
    }

    @Transactional
    public Boolean disableUser(UUID id) {
        final UserEntity user = this.getUserOrThrow(id);
        if (user.getDeletedAt() != null) throw new UserException("Usuário já desativado");
        user.setDeletedAt(LocalDateTime.now());
        repository.diableById(id);
        this.repository.save(user);
        return true;
    }
}
