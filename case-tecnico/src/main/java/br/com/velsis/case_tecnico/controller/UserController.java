package br.com.velsis.case_tecnico.controller;


import br.com.velsis.case_tecnico.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.dto.response.GetUserResponseDTO;
import br.com.velsis.case_tecnico.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.mapper.UserMapper;
import br.com.velsis.case_tecnico.service.domain.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<GetUserResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.service.findAll()
                        .stream()
                        .map(UserMapper::toGetResponse)
                        .toList()
        );
    }
}
