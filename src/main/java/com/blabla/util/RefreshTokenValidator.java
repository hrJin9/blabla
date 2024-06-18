package com.blabla.util;

import com.blabla.exception.TokenNotValidException;
import com.blabla.repository.auth.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenValidator {
    private final TokenGenerator tokenGenerator;
    private final BlackListRepository blackListRepository;

    public boolean validateToken(String refreshToken, Long id) {
        if (!refreshTokenValidation(refreshToken, id)) {
            throw new TokenNotValidException("유효하지 않은 refresh Token 입니다.");
        }
        return true;
    }

    private boolean refreshTokenValidation(String refreshToken, Long id) {
        return isBearerToken(refreshToken) &&
                isValidToken(refreshToken) &&
                validateTokenMemberId(refreshToken, id) &&
                validateLogoutToken(refreshToken);
    }

    private boolean isBearerToken(String refreshToken) {
        return BearerParser.isBearerAuthType(refreshToken);
    }

    public boolean isValidToken(String refreshToken) {
        return tokenGenerator.isValidToken(BearerParser.parseAuthorization(refreshToken));
    }

    public boolean validateTokenMemberId(String refreshToken, Long id) {
        Long memberId = tokenGenerator.extractMemberId(BearerParser.parseAuthorization(refreshToken));
        return memberId.equals(id);
    }

    public boolean validateLogoutToken(String refreshToken) {
        return blackListRepository.findByInvalidRefreshToken(refreshToken).isEmpty();
    }
}
