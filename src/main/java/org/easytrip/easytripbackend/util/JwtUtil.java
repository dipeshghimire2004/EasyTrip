package org.easytrip.easytripbackend.util;

//import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;

//    convert the secret string to secret key
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email, Set<String> roles) {
        Map<String, Object> claims =new HashMap<>();
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expiration)))
                .signWith(getSecretKey(),SignatureAlgorithm.HS512).
                compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(refreshExpiration)))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    public String extractEmail(String token){
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Set<String> extractRoles(String token){
        return (Set<String>) Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles");
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public String refreshAccessToken(String refreshToken){
        if(validateToken(refreshToken)){
            String email = extractEmail(refreshToken);
            Set<String> roles = extractRoles(refreshToken);
            return generateToken(email, roles);
        }
        throw new RuntimeException("Invalid refresh token");
    }
}


//The JwtUtil class is a utility for handling JWTs in a Spring Boot application. It provides methods for generating,
//validating, and extracting information from tokens. While this implementation is functional,
//it should be enhanced for production use by securing the secret key, using stronger algorithms,
//and implementing additional security measures.