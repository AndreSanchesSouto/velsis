package br.com.velsis.case_tecnico.infrastructure.security;

import br.com.velsis.case_tecnico.domain.entity.UserEntity;
import br.com.velsis.case_tecnico.domain.repository.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //  serviço gerenciado pelo ecossistema do Spring
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Injeção de dependência do repositório de usuários
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Metodo padrão do Spring Security para buscar o usuário no banco de dados durante o fluxo de autenticação.
     * Recebe o identificador (neste caso, o login) enviado na requisição ou extraído do token JWT.
     */
    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByLogin(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado")
                );

        // Converte a entidade de domínio 'UserEntity' para o objeto 'UserDetails' que é o Spring Security entende como
        // User
        return User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .disabled(user.getDeletedAt() != null)
                .build();
    }

}