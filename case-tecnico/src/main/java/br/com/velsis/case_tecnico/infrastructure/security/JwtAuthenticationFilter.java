package br.com.velsis.case_tecnico.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // Registra a classe como um componente Spring gerenciável
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    // dependências
    public JwtAuthenticationFilter( JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Intercepta para extrair, validar o token JWT e autenticar o usuário no Spring.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Pega o cabeçalho 'Authorization' enviado na requisição HTTP
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null ||
            !authorizationHeader.startsWith("Bearer ")
        ) {
            filterChain.doFilter(request, response); // Segue o fluxo normal da aplicação
            return;
        }

        // Extrai o token removendo os primeiros 7 caracteres ( "Bearer " )
        final String token = authorizationHeader.substring(7);

        try {
            final String username = jwtService.extractUsername(token);
            if (username != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                // Busca as informações cadastrais e permissões do usuário no banco de dados
                var userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(token, userDetails)) {
                    // Valida a integridade, assinatura e tempo de expiração do token contra os dados obtidos
                    var authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                    );
                    // Insere o objeto de autenticação preenchido dentro do contexto global de segurança do Spring Security
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception ignored) {
            // Caso ocorra erro de token malformado, assinatura inválida ou expirado, captura a exceção de forma silenciosa.
            // A requisição continua sem autenticação no Spring e falhará posteriormente nas rotas protegidas (retornando 401).
        }

        filterChain.doFilter(request, response);
    }
}
