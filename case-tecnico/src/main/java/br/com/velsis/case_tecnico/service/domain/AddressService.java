package br.com.velsis.case_tecnico.service.domain;

import br.com.velsis.case_tecnico.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.dto.response.GetAddressResponseDTO;
import br.com.velsis.case_tecnico.dto.response.PostAddressResponseDTO;
import br.com.velsis.case_tecnico.entity.AddressEntity;
import br.com.velsis.case_tecnico.entity.UserEntity;
import br.com.velsis.case_tecnico.factory.AddressFactory;
import br.com.velsis.case_tecnico.mapper.AddressMapper;
import br.com.velsis.case_tecnico.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public AddressEntity post(UserEntity user, PostAddressRequestDTO requestDTO) {
        final AddressEntity address = AddressFactory.create(requestDTO);
        user.addAddress(address);
        return this.repository.save(address);
    }

    public List<AddressEntity> findAllAddresses() {
        return this.repository.findAll();
    }

}
