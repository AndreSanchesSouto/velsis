package br.com.velsis.case_tecnico.service;

import br.com.velsis.case_tecnico.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.dto.response.PostAddressResponseDTO;
import br.com.velsis.case_tecnico.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.entity.AddressEntity;
import br.com.velsis.case_tecnico.entity.UserEntity;
import br.com.velsis.case_tecnico.factory.AddressFactory;
import br.com.velsis.case_tecnico.factory.UserFactory;
import br.com.velsis.case_tecnico.mapper.AddressMapper;
import br.com.velsis.case_tecnico.mapper.UserMapper;
import br.com.velsis.case_tecnico.repository.AddressRepository;
import br.com.velsis.case_tecnico.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public PostAddressResponseDTO post(PostAddressRequestDTO requestDTO) {
        AddressEntity address = AddressFactory.create(requestDTO);
        AddressEntity addressCreated = this.repository.save(address);
        return AddressMapper.toResponse(addressCreated);
    }

}
