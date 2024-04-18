package com.blabla.config.resolver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthInfo {
    private final Long id;
    private final String nickName;
    private final String email;
}
