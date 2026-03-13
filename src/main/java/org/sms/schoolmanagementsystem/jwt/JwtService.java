package org.sms.schoolmanagementsystem.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key key;
    private final TokenCacheService tokenCacheService;

    public JwtService(Key jwtSigningKey, TokenCacheService tokenCacheService) {
        this.key = jwtSigningKey;
        this.tokenCacheService = tokenCacheService;
    }
    @Value("${jwt.expirationTime}")
    private Long jwtExpirationInMs;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +jwtExpirationInMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        String cachedToken = tokenCacheService.getCachedToken(username);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && cachedToken != null
                && cachedToken.equals(token));
    }
}
