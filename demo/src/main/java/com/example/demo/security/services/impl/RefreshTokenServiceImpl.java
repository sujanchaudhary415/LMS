package com.example.demo.security.services.impl;

import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.exception.TokenRefreshException;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.security.jwt.JwtProperties;
import com.example.demo.security.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpiration()));
        refreshToken.setUser(user);
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);

            throw new TokenRefreshException(
                    "Refresh token was expired. Please make a new signin request"
            );
        }
       return refreshToken;
    }

    @Override
    @Transactional
    public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
        oldToken.setToken(UUID.randomUUID().toString());
        oldToken.setExpiryDate(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpiration()));
        return refreshTokenRepository.save(oldToken);
    }

    @Override
    @Transactional
    public void deleteByToken(String refreshToken) {
         refreshTokenRepository.deleteByToken(refreshToken);
    }

    @Override
    @Transactional
    public void deleteByUser(User user) {
         refreshTokenRepository.deleteByUser(user);
    }
}
