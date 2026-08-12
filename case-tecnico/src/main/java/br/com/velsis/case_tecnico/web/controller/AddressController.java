package br.com.velsis.case_tecnico.web.controller;


import br.com.velsis.case_tecnico.application.dto.request.PostAddressRequestDTO;
import br.com.velsis.case_tecnico.application.dto.response.GetAddressResponseDTO;
import br.com.velsis.case_tecnico.application.dto.response.PostAddressResponseDTO;
import br.com.velsis.case_tecnico.application.mapper.AddressMapper;
import br.com.velsis.case_tecnico.application.service.AddressUserApplicationService;
import br.com.velsis.case_tecnico.domain.service.AddressService;
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
    private final AddressUserApplicationService addressUserApplicationService;

    public AddressController(AddressService service, AddressUserApplicationService addressUserApplicationService) {
        this.service = service;
        this.addressUserApplicationService = addressUserApplicationService;
    }

    @PostMapping("user/{userId}")
    public ResponseEntity<PostAddressResponseDTO> post(
            @PathVariable UUID userId,
            @Valid @RequestBody PostAddressRequestDTO requestDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                AddressMapper.toPostResponse(this.addressUserApplicationService.addAddressToUser(userId, requestDTO))
        );
    }

    @GetMapping
    public ResponseEntity<List<GetAddressResponseDTO>> findAllAddresses() {
        return ResponseEntity.status(HttpStatus.OK).body(
                    this.service.findAll()
                        .stream()
                        .map(AddressMapper::toGetResponse)
                        .toList()
        );
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<GetAddressResponseDTO>> findAddressesByUser(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.addressUserApplicationService.findAddressesByUserId(userId)
                        .stream()
                        .map(AddressMapper::toGetResponse)
                        .toList()
        );
    }

}
