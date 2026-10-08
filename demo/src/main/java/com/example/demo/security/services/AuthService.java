package com.example.demo.security.services;
import com.example.demo.security.request.LoginRequest;
import com.example.demo.security.request.SignupRequest;
import com.example.demo.security.response.AuthResponse;
import com.example.demo.security.response.LoginResult;
import com.example.demo.security.response.LogoutResponse;
import com.example.demo.security.response.RefreshTokenResult;

public interface AuthService {
    AuthResponse Signup(SignupRequest request);
    LoginResult Signin(LoginRequest request);
    RefreshTokenResult refreshAccessToken(String refreshToken);
    LogoutResponse logout(String refreshToken);
}
