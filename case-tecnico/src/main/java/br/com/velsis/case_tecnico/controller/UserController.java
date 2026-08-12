package br.com.velsis.case_tecnico.controller;


import br.com.velsis.case_tecnico.dto.request.PostUserRequestDTO;
import br.com.velsis.case_tecnico.dto.response.PostUserResponseDTO;
import br.com.velsis.case_tecnico.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(HttpStatus.OK).body(this.service.post(requestDTO));
    }

}
