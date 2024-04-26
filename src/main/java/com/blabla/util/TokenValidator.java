package com.blabla.util;

import com.blabla.exception.UnAuthorizationException;
import com.blabla.repository.auth.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {
    private final TokenGenerator tokenGenerator;
    private final BlackListRepository blackListRepository;

    public void validateToken(String refreshToken, Long id) {
        this.isBearerToken(refreshToken);
        this.isValidToken(refreshToken);
        this.validateTokenMemberId(refreshToken, id);
        this.validateLogoutToken(refreshToken);
    }

    public void isBearerToken(String refreshToken) {
        if (!BearerParser.isBearerAuthType(refreshToken)) {
            throw new UnAuthorizationException("인증 가능한 refresh token이 아닙니다.");
        }
    }

    public void isValidToken(String refreshToken) {
        if (!tokenGenerator.isValidToken(refreshToken)) {
            throw new UnAuthorizationException("유효하지 않은 refresh token입니다.");
        }
    }

    public void validateTokenMemberId(String refreshToken, Long id) {
        Long memberId = tokenGenerator.extractMemberId(refreshToken);
        if (!memberId.equals(id)) {
            throw new UnAuthorizationException("로그인한 사용자의 refresh token이 아닙니다.");
        }
    }

    public void validateLogoutToken(String refreshToken) {
        if (blackListRepository.findByInvalidRefreshToken(refreshToken).isPresent()) {
            throw new UnAuthorizationException("이미 로그아웃된 사용자입니다.");
        }
    }
}
