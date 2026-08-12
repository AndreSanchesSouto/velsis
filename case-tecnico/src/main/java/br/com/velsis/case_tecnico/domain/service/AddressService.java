package br.com.velsis.case_tecnico.domain.service;

import br.com.velsis.case_tecnico.application.dto.request.PatchAddressRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.application.dto.response.GetAddressResponseDTO;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.application.factory.AddressFactory;
import br.com.velsis.case_tecnico.domain.exception.AddressException;
import br.com.velsis.case_tecnico.domain.repository.AddressRepository;
import br.com.velsis.case_tecnico.infrastructure.external.ViaCepClient;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository repository;
    private final UserService userService;

    public AddressService(AddressRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public AddressEntity post(UserEntity user, PostAddressRequestDTO requestDTO) {
        final AddressEntity address = AddressFactory.create(requestDTO);
        ViaCepClient.consultZipcode(address.getZipcode());
        user.addAddress(address);
        return this.repository.save(address);
    }

    public List<AddressEntity> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public AddressEntity addAddressToUser(UUID userId, PostAddressRequestDTO requestDTO) {
        final UserEntity user = this.userService.getUserOrThrow(userId);
        return this.post(user, requestDTO);
    }

    public List<AddressEntity> findAddressesByUserId(UUID userId) {
        return this.repository.findByUserId(userId);
    }

    public AddressEntity getAddressOrThrow(UUID id) {
        return this.repository.findById(id)
                .orElseThrow( () -> new AddressException("Endereço não encontrado")
        );
    }

    @Transactional
    public AddressEntity updateAddress(UUID id, PatchAddressRequestDTO requestDTO) {
        final AddressEntity target = this.getAddressOrThrow(id);
        final AddressEntity updated = AddressFactory.update(target, requestDTO);
        return repository.save(updated);
    }

    public AddressEntity findById(UUID id) {
        return this.getAddressOrThrow(id);
    }

    @Transactional
    public Boolean disableAddress(UUID id) {
        final AddressEntity target = this.getAddressOrThrow(id);
        target.setDeletedAt(LocalDateTime.now());
        this.repository.save(target);
        return true;
    }
}