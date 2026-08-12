package br.com.velsis.case_tecnico.service.domain;

import br.com.velsis.case_tecnico.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.dto.response.GetUserResponseDTO;
import br.com.velsis.case_tecnico.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.entity.UserEntity;
import br.com.velsis.case_tecnico.exception.UserException;
import br.com.velsis.case_tecnico.factory.UserFactory;
import br.com.velsis.case_tecnico.mapper.UserMapper;
import br.com.velsis.case_tecnico.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
