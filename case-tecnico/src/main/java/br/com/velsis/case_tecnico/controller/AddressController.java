package br.com.velsis.case_tecnico.controller;


import br.com.velsis.case_tecnico.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.dto.response.GetAddressResponseDTO;
import br.com.velsis.case_tecnico.dto.response.PostAddressResponseDTO;
import br.com.velsis.case_tecnico.mapper.AddressMapper;
import br.com.velsis.case_tecnico.service.application.UserAddressApplicationService;
import br.com.velsis.case_tecnico.service.domain.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("addresses")
public class AddressController {

    private final AddressService service;
    private final UserAddressApplicationService userAddressApplicationService;

    public AddressController(AddressService service, UserAddressApplicationService userAddressApplicationService) {
        this.service = service;
        this.userAddressApplicationService = userAddressApplicationService;
    }

    @PostMapping("user/{userId}")
    public ResponseEntity<PostAddressResponseDTO> post(
            @PathVariable UUID userId,
            @Valid @RequestBody PostAddressRequestDTO requestDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                AddressMapper.toPostResponse(this.userAddressApplicationService.addAddressToUser(userId, requestDTO))
        );
    }

    @GetMapping
    public ResponseEntity<List<GetAddressResponseDTO>> findAllAddresses() {
        return ResponseEntity.status(HttpStatus.OK).body(
                    this.service.findAllAddresses()
                        .stream()
                        .map(AddressMapper::toGetResponse)
                        .toList()
        );
    }

}
