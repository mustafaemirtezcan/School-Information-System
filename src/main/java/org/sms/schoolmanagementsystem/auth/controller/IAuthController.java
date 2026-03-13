package org.sms.schoolmanagementsystem.auth.controller;
import org.sms.schoolmanagementsystem.auth.dto.AuthRequest;
import org.sms.schoolmanagementsystem.auth.dto.AuthResponse;
import org.sms.schoolmanagementsystem.auth.dto.TokenRequest;
import org.sms.schoolmanagementsystem.user.UserDto;

public interface IAuthController {

    UserDto register(AuthRequest authRequest);
    AuthResponse login(AuthRequest authRequest);
    AuthResponse refreshToken(TokenRequest tokenRequest);
    String logout(TokenRequest tokenRequest);
}
