package com.gft.auth.security;

import com.gft.auth.model.Role;
import com.gft.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    @Value("${jwt.prefix}")
    private String prefix;

    private SecretKey getSigningKey() {
        // garante que sempre use UTF-8
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gera token incluindo roles como claim.
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        claims.put("roles", roles);

        SecretKey key = getSigningKey();

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return prefix + " " + token;
    }

    /**
     * Extrai todas as claims do ‘token’. Lança JwtException se inválido.
     */
    public Claims extractAllClaims(String token) throws JwtException {
        SecretKey key = getSigningKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extrai o username (subject).
     */
    public String extractUsername(String token) {
        try {
            return extractAllClaims(token).getSubject();
        } catch (JwtException ex) {
            // propagar ou logar conforme sua política. Aqui rethrow para o chamador tratar.
            throw ex;
        }
    }


    /**
     * Verifica se o token está expirado.
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (JwtException ex) {
            // se inválido, consideramos como expirado/ inválido
            return true;
        }
    }

    /**
     * Valida ‘token’: checa se o username bate e se não está expirado.
     * Retorna false em qualquer exceção de parsing.
     */
    public boolean validateToken(String token, String username) {
        try {
            String tokenUsername = extractUsername(token);
            return username.equals(tokenUsername) && !isTokenExpired(token);
        } catch (JwtException ex) {
            return false;
        }
    }
}