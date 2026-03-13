package org.sms.schoolmanagementsystem.auth.controller.impl;

import jakarta.validation.Valid;
import org.sms.schoolmanagementsystem.auth.controller.IAuthController;
import org.sms.schoolmanagementsystem.auth.dto.AuthRequest;
import org.sms.schoolmanagementsystem.auth.dto.AuthResponse;
import org.sms.schoolmanagementsystem.auth.dto.TokenRequest;
import org.sms.schoolmanagementsystem.auth.service.IAuthService;
import org.sms.schoolmanagementsystem.user.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerImpl implements IAuthController {

    private final IAuthService authService;

    public AuthControllerImpl(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Override
    public UserDto register(@RequestBody @Valid AuthRequest authRequest) {
        return authService.register(authRequest);
    }
    @PostMapping("/login")
    @Override
    public AuthResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return authService.login(authRequest);
    }
    @PostMapping("/refreshToken")
    @Override
    public AuthResponse refreshToken(@RequestBody TokenRequest tokenRequest) {
        return authService.refreshToken(tokenRequest);
    }
    @PostMapping("/logout")
    @Override
    public String logout(@RequestBody  TokenRequest tokenRequest) {
        return authService.logout(tokenRequest);
    }

}
