package br.com.velsis.case_tecnico.web.controller;

import br.com.velsis.case_tecnico.application.dto.request.PostAuthenticationRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostRegisterRequestDTO;
import br.com.velsis.case_tecnico.application.dto.response.LoginResponseDTO;
import br.com.velsis.case_tecnico.domain.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping("login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody PostAuthenticationRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                new LoginResponseDTO(authenticationService.login(requestDTO))
        );
    }

    @RequestMapping("register")
    public ResponseEntity<Boolean> register(@Valid @RequestBody PostRegisterRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authenticationService.register(requestDTO));
    }

}
