package br.com.velsis.case_tecnico.application.service;

import br.com.velsis.case_tecnico.application.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.domain.entity.AddressEntity;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.domain.service.AddressService;
import br.com.velsis.case_tecnico.domain.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressUserApplicationService {
    private final UserService userService;
    private final AddressService addressService;

    public AddressUserApplicationService(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @Transactional
    public AddressEntity addAddressToUser(UUID userId, PostAddressRequestDTO addressRequestDTO) {
        final UserEntity user = this.userService.getUserOrThrow(userId);
        return this.addressService.post(user, addressRequestDTO);
    }

    public List<AddressEntity> findAddressesByUserId(UUID userId) {
        final UserEntity user = this.userService.getUserOrThrow(userId);
        return this.addressService.findByUserId(user);
    }
}
