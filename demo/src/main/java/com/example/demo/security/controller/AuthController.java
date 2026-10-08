package com.example.demo.security.controller;

import com.example.demo.security.cookie.CookieService;
import com.example.demo.security.request.LoginRequest;
import com.example.demo.security.request.SignupRequest;
import com.example.demo.security.response.AuthResponse;
import com.example.demo.security.response.LoginResult;
import com.example.demo.security.response.LogoutResponse;
import com.example.demo.security.response.RefreshTokenResult;
import com.example.demo.security.services.AuthService;
import com.example.demo.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name="Auth",description = "Auth management APIs")
public class AuthController {

    private final AuthService authService;
    private final CookieService cookieService;

    @PostMapping("/signup")
    @Operation(summary = "Register a user")
    public ResponseEntity<ApiResponse<AuthResponse>>Signup(@Valid @RequestBody SignupRequest request){
           AuthResponse response =authService.Signup(request);
           return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User created successfully",response));
    }

    @PostMapping("/signin")
    @Operation(summary = "Sign in user")
    public ResponseEntity<ApiResponse<AuthResponse>>Signin(@Valid @RequestBody LoginRequest request){
        LoginResult result=authService.Signin(request);

        HttpHeaders headers=new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,result.getAccessCookie().toString());
        headers.add(HttpHeaders.SET_COOKIE,result.getRefreshCookie().toString());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(ApiResponse.success("Login successful",result.getAuthResponse()));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh accessToken")
    public ResponseEntity<ApiResponse<RefreshTokenResult>>refreshToken(HttpServletRequest request){
        String refreshToken= cookieService.getRefreshTokenFromCookie(request);
        RefreshTokenResult result=authService.refreshAccessToken(refreshToken);

        HttpHeaders headers=new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,result.getAccessCookie().toString());
        headers.add(HttpHeaders.SET_COOKIE,result.getRefreshCookie().toString());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(ApiResponse.success("Token refreshed successfully"));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout the current user")
    public ResponseEntity<ApiResponse<LogoutResponse>>logout(HttpServletRequest request){
        String refreshToken=cookieService.getRefreshTokenFromCookie(request);
        LogoutResponse response=authService.logout(refreshToken);

        HttpHeaders headers=new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,response.getAccessCookie().toString());
        headers.add(HttpHeaders.SET_COOKIE,response.getRefreshCookie().toString());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(ApiResponse.success("Logout successful"));
    }
}
