package com.blabla.api.auth.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthTokenResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String grantType;
    private final Long expiresIn;

    public static AuthTokenResponse of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthTokenResponse(accessToken, refreshToken, grantType, expiresIn);
    }
}
