package br.com.velsis.case_tecnico.web.controller;

import br.com.velsis.case_tecnico.application.dto.request.PatchUserRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.application.dto.response.GetUserResponseDTO;
import br.com.velsis.case_tecnico.application.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.application.mapper.UserMapper;
import br.com.velsis.case_tecnico.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PostUserResponseDTO> post(
            @Valid
            @RequestBody
            PostUserRequestDTO requestDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                UserMapper.toPostResponse(this.service.post(requestDTO))
        );
    }

    @GetMapping
    public ResponseEntity<Page<GetUserResponseDTO>> findAll(
            @RequestParam(required = false, defaultValue = "") String search,
            @PageableDefault(size = 100) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.service.findAllActives(search, pageable)
                        .map(UserMapper::toGetResponse)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponseDTO> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserMapper.toGetResponse(this.service.findById(id)));
    }

    @PatchMapping("{id}")
    public ResponseEntity<GetUserResponseDTO> update(
            @PathVariable UUID id,
            @Valid
            @RequestBody
            PatchUserRequestDTO requestDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserMapper.toGetResponse(this.service.update(id, requestDTO)));
    }

    @PatchMapping("disable/{id}")
    public ResponseEntity<Boolean> disableUser(
            @PathVariable UUID id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.service.disableUser(id));
    }
}
