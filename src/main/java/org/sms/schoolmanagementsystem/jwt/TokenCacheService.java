package org.sms.schoolmanagementsystem.jwt;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TokenCacheService {
    @CachePut(value = "tokens", key = "#username")
    public String cacheToken(String username, String token) {
        return token;
    }
    @Cacheable(value = "tokens", key = "#username")
    public String getCachedToken(String username) {
        return null;
    }
    @CacheEvict(value = "tokens", key = "#username")
    public boolean evictToken(String username) {
        return true;
    }
}
