package br.com.velsis.case_tecnico.domain.service;

import br.com.velsis.case_tecnico.application.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.application.factory.AddressFactory;
import br.com.velsis.case_tecnico.domain.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<AddressEntity> findAll() {
        return this.repository.findAll();
    }

    public List<AddressEntity> findByUserId(UserEntity user) {
        return this.repository.findByUserId(user.getId());
    }
}
