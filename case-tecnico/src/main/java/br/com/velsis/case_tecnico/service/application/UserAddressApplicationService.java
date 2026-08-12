package br.com.velsis.case_tecnico.service.application;

import br.com.velsis.case_tecnico.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.entity.AddressEntity;
import br.com.velsis.case_tecnico.entity.UserEntity;
import br.com.velsis.case_tecnico.service.domain.AddressService;
import br.com.velsis.case_tecnico.service.domain.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAddressApplicationService {
    private final UserService userService;
    private final AddressService addressService;

    public UserAddressApplicationService(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @Transactional
    public AddressEntity addAddressToUser(UUID userId, PostAddressRequestDTO addressRequestDTO) {
        final UserEntity user = this.userService.getUserOrThrow(userId);
        return this.addressService.post(user, addressRequestDTO);
    }
}
