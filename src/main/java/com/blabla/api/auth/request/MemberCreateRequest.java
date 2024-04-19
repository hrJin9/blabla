package com.blabla.api.auth.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateRequest {
    private final String email;
    private final String password;
    private final String nickName;
    private final String phone;
}
