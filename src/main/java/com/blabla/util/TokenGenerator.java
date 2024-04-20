package com.blabla.util;

import com.blabla.api.auth.response.AuthTokenResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Ref;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class TokenGenerator {
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;       // 24시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokenResponse generate(Long id) {
        Long now = (new Date()).getTime();
        Date accessTokenExpiresAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = id.toString();

        String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiresAt);
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiresAt);

        return AuthTokenResponse.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public boolean isValidToken(String accessToken) {
        return jwtTokenProvider.isValidToken(accessToken);
    }

    public Long extractMemberId(String accessToken) {
        return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
    }

}
