package br.com.velsis.case_tecnico.service;

import br.com.velsis.case_tecnico.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.entity.UserEntity;
import br.com.velsis.case_tecnico.factory.UserFactory;
import br.com.velsis.case_tecnico.mapper.UserMapper;
import br.com.velsis.case_tecnico.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public PostUserResponseDTO post(PostUserRequestDTO requestDTO) {
        UserEntity user = UserFactory.create(requestDTO);
        UserEntity userCreated = this.repository.save(user);
        return UserMapper.toResponse(userCreated);
    }

}
