package br.com.velsis.case_tecnico.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service // Define a classe como um componente de serviço gerenciado pelo Spring
public class JwtService {

    private final SecretKey secretKey;
    private final long expiration;

    // Construtor que recebe os valores configurados no arquivo application.properties
    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        // Transforma a string secreta em uma chave criptográfica HMAC segura do tipo SecretKey
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration; // Tempo de vida do token em ms
    }

    /**
     * Gera um token JWT com base nos dados do usuário autenticado.
     */
    public String generateToken(UserDetails userDetails) {

        final Date issuedAt = new Date(); // Data/Hora atual de criação do token
        final Date expirationDate = new Date(issuedAt.getTime() + expiration); // Calcula o exato momento de expiração

        return Jwts.builder()
                .subject(userDetails.getUsername()) // Define o identificador principal do token (ex: e-mail ou username)
                .issuedAt(issuedAt) // Adiciona a data de emissão (claim 'iat')
                .expiration(expirationDate) // Adiciona a data de expiração (claim 'exp')
                .signWith(secretKey) // Assina digitalmente o token usando a chave secreta criptográfica
                .compact(); // Constrói o JWT e o transforma em uma String final compactada
    }

    /**
     * Extrai o nome de usuário (subject) de dentro do token informado.
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Valida se o token pertence ao usuário correto e se ainda está dentro do prazo de validade.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // O token é válido se o username extraído for idêntico ao do banco e o token não tiver expirado
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    /**
     * Descriptografa, valida a assinatura e extrai todas as informações (Claims) contidas no payload do token.
     */
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey) // Fornece a chave secreta para validar a integridade da assinatura
                .build() // Constrói o parser de validação do JWT
                .parseSignedClaims(token) // Analisa e valida a assinatura digital do token (lança exceções se for inválido/expirado)
                .getPayload(); // Retorna o corpo (payload) do token contendo as Claims
    }
}