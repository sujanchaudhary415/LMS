package com.example.demo.security.services.impl;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.enums.AppRole;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.TokenRefreshException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.cookie.CookieService;
import com.example.demo.security.jwt.JwtService;
import com.example.demo.security.mapper.AuthMapper;
import com.example.demo.security.request.LoginRequest;
import com.example.demo.security.request.SignupRequest;
import com.example.demo.security.response.AuthResponse;
import com.example.demo.security.response.LoginResult;
import com.example.demo.security.response.LogoutResponse;
import com.example.demo.security.response.RefreshTokenResult;
import com.example.demo.security.services.AuthService;
import com.example.demo.security.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CookieService cookieService;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse Signup(SignupRequest request) {
       String email=request.getEmail().trim().toLowerCase(Locale.ROOT);
       String firstName= request.getFirstName().trim().replaceAll("\\s+","");
       String lastName= request.getLastName().trim().replaceAll("\\s+","");

       Set<AppRole> requestedRoles=request.getRoles();
       if(requestedRoles.contains(AppRole.ADMIN)){
           throw new IllegalArgumentException(
                   "ADMIN role can not be assigned during signup"
           );
       }

       if(userRepository.existsByEmailIgnoreCase(email)){
           throw new DuplicateResourceException("Email already exists");
       }

       User user=authMapper.toEntity(request);
       user.setEmail(email);
       user.setFirstName(firstName);
       user.setLastName(lastName);

       user.setRoles(new HashSet<>(requestedRoles));
       user.setPassword(passwordEncoder.encode(request.getPassword()));

       User savedUser=userRepository.save(user);
       return authMapper.toResponse(savedUser);
    }

    @Override
    public LoginResult Signin(LoginRequest request) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();

        User user=userRepository.findByEmailIgnoreCase(userDetails.getEmail()).orElseThrow(()->new UsernameNotFoundException("User not found"));

        ResponseCookie accessCookie=cookieService.generateAccessTokenCookie(userDetails);

        RefreshToken refreshToken=refreshTokenService.createRefreshToken(user);
        ResponseCookie refreshCookie=cookieService.generateRefreshTokenCookie(refreshToken.getToken());
        AuthResponse authResponse =authMapper.toResponse(user);

        return new LoginResult(
                authResponse,
                accessCookie,
                refreshCookie
        );
    }

    @Override
    public RefreshTokenResult refreshAccessToken(String refreshToken) {
        if(refreshToken==null || refreshToken.isBlank()){
            throw new TokenRefreshException("Refresh token is missing");
        }

        RefreshToken storedToken =refreshTokenService.findByToken(refreshToken).orElseThrow(()->new RuntimeException("Refresh token not found"));
        refreshTokenService.verifyExpiration(storedToken);
        User user=storedToken.getUser();
        UserDetailsImpl userDetails=UserDetailsImpl.build(user);

        RefreshToken rotatedToken=refreshTokenService.rotateRefreshToken(storedToken);

        ResponseCookie accessCookie=cookieService.generateAccessTokenCookie(userDetails);
        ResponseCookie refreshCookie=cookieService.generateRefreshTokenCookie(rotatedToken.getToken());

        return new RefreshTokenResult(
                accessCookie,
                refreshCookie
        );
    }

    @Override
    public LogoutResponse logout(String refreshToken) {
        if(refreshToken!=null && !refreshToken.isBlank()){
            refreshTokenService.deleteByToken(refreshToken);
        }

        ResponseCookie cleanAccessCookie=cookieService.cleanAccessTokenCookie();
        ResponseCookie cleanRefreshCookie=cookieService.cleanRefreshTokenCookie();

        return new LogoutResponse(
                cleanAccessCookie,
                cleanRefreshCookie
        );
    }


}
