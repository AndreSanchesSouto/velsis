package br.com.velsis.case_tecnico.web.controller;

import br.com.velsis.case_tecnico.application.dto.request.PostAuthenticationRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostRegisterRequestDTO;
import br.com.velsis.case_tecnico.application.dto.response.LoginResponseDTO;
import br.com.velsis.case_tecnico.application.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Aqui está localizado o Controlelr de Authentication, basicamente ele é o responsável por receber as chamadas
 * REST e envia essa requisição ao AuthenticationService, para que nele seja feito a lógica para responder a chamada.
 * Aqui tem somente POST.
 * */
@RestController // injeta como controller no spring
@RequestMapping("authentication") // nome da rota
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) { // inejeção do service
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody PostAuthenticationRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(authenticationService.login(requestDTO));
    }

    @PostMapping("register")
    public ResponseEntity<Boolean> register(@Valid @RequestBody PostRegisterRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authenticationService.register(requestDTO));
    }

}
