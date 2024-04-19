package com.blabla.api.auth.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberLoginRequest {
    private final String email;
    private final String password;
}
