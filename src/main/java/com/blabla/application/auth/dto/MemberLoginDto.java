package com.blabla.application.auth.dto;

import com.blabla.api.auth.request.MemberLoginRequest;

public record MemberLoginDto(String email,
                             String password) {

    public static MemberLoginDto from(MemberLoginRequest request) {
        return new MemberLoginDto(
                request.email(),
                request.password()
        );
    }
}
