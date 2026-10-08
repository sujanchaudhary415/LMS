package com.example.demo.security.services;

import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    Optional<RefreshToken>findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    RefreshToken rotateRefreshToken(RefreshToken oldToken);
    void deleteByToken(String refreshToken);
    void deleteByUser(User user);

}
