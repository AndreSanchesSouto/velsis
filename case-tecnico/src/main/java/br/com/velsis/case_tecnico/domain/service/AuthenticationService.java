package br.com.velsis.case_tecnico.domain.service;

import br.com.velsis.case_tecnico.application.dto.request.PostAuthenticationRequestDTO;
import br.com.velsis.case_tecnico.application.dto.request.PostRegisterRequestDTO;
import br.com.velsis.case_tecnico.application.factory.UserFactory;
import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.domain.exception.PasswordMismatchException;
import br.com.velsis.case_tecnico.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public Boolean register(PostRegisterRequestDTO requestDTO)  {
        if(
            !requestDTO.authentication().password()
                .equals(requestDTO.authentication().confirmPassword())
        ) {
            throw new PasswordMismatchException("Senhas não coincidem");
        }

        final UserEntity user = UserFactory.register(requestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.register(user);
        return true;
    }

    public String login(PostAuthenticationRequestDTO requestDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.login(),
                        requestDTO.password()
                )
        );
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return token;
    }
}
