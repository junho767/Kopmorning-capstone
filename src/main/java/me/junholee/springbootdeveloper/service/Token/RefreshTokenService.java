package me.junholee.springbootdeveloper.service.Token;

import lombok.RequiredArgsConstructor;
import me.junholee.springbootdeveloper.domain.RefreshToken;
import me.junholee.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token.js"));
    }
}