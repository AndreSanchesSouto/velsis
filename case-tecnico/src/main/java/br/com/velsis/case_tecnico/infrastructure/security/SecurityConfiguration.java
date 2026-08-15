package br.com.velsis.case_tecnico.infrastructure.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Define que esta é uma classe de configuração do Spring
@EnableWebSecurity // Ativa e customiza a segurança web do Spring Security na aplicação
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Injeção de dependência via construtor para o serviço de usuários e o filtro JWT
    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean // Define a cadeia de filtros de segurança (Security Filter Chain)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Ativa a configuração padrão de CORS (Cross-Origin Resource Sharing)
                .cors(Customizer.withDefaults())

                // Desativa a proteção CSRF, pois a API usa tokens JWT e não cookies/sessões
                .csrf(AbstractHttpConfigurer::disable)

                // Configura a política de sessão como STATELESS (sem estado), obrigatória para APIs REST
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Configura as regras de autorização de rotas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authentication/**").permitAll()
                        .anyRequest().authenticated()
                )

                // Customiza o tratamento de erros de segurança
                .exceptionHandling(exception -> exception
                        // Trata erro de usuário não autenticado (retorna HTTP 401 Unauthorized)
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                        // Trata erro de usuário sem permissão para o recurso (retorna HTTP 403 Forbidden)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        })
                )
                // Adiciona o filtro JWT customizado antes do filtro padrão de autenticação por usuário/senha
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                // Vincula o serviço customizado de busca de usuários nas tabelas do banco de dados
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean // Define o algoritmo de criptografia BCrypt para encriptar e validar senhas com segurança
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // Expõe o AuthenticationManager como um Bean para ser usado no fluxo de login manual
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
