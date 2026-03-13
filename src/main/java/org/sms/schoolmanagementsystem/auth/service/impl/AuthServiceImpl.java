package org.sms.schoolmanagementsystem.auth.service.impl;

import org.sms.schoolmanagementsystem.auth.dto.AuthRequest;
import org.sms.schoolmanagementsystem.auth.dto.AuthResponse;
import org.sms.schoolmanagementsystem.auth.dto.TokenRequest;
import org.sms.schoolmanagementsystem.auth.service.IAuthService;
import org.sms.schoolmanagementsystem.exception.AuthException.AuthException;
import org.sms.schoolmanagementsystem.jwt.JwtService;
import org.sms.schoolmanagementsystem.jwt.TokenCacheService;
import org.sms.schoolmanagementsystem.user.User;
import org.sms.schoolmanagementsystem.user.UserDto;
import org.sms.schoolmanagementsystem.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenCacheService tokenCacheService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, TokenCacheService tokenCacheService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenCacheService = tokenCacheService;
    }

    @Override
    public UserDto register(AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser=userRepository.save(user);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(savedUser, userDto);
        return userDto;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        if(tokenCacheService.getCachedToken(request.getUsername()) == null) {
        authenticate(request.getUsername(), request.getPassword());
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AuthException("User not found"));
        user.setLastLogin(new java.util.Date());
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        String cachedToken= tokenCacheService.cacheToken(user.getUsername(), token);
        return new AuthResponse(cachedToken);}
        else {
            throw new AuthException("User already logged in");
        }
    }

    @Override
    public AuthResponse refreshToken(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String cachedToken=tokenCacheService.getCachedToken(username);
        if(cachedToken == null) {
            throw new AuthException("No cached token found for user: " + username);
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AuthException("User not found"));
        if(jwtService.isTokenValid(cachedToken, user)) {
            String newToken = jwtService.generateToken(user);
            return new AuthResponse(tokenCacheService.cacheToken(username, newToken));
        }
        else {
            throw new AuthException("Last token expired or invalid for user: " + username+"Please login again.");
        }
    }
    @Override
    public String logout(TokenRequest tokenRequest) {
        if(tokenCacheService.getCachedToken(tokenRequest.getUsername()) == null) {
            throw new AuthException("User " + tokenRequest.getUsername()+ " is not logged in or token not found.");
        }
        boolean result=tokenCacheService.evictToken(tokenRequest.getUsername());
        SecurityContextHolder.clearContext();
        return result ? "Logout successful" : "Logout failed";
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            throw new AuthException("Invalid username or password");
        }
    }
}
