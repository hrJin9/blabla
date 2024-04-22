package com.blabla.application.auth.dto;

import com.blabla.api.auth.request.MemberCreateRequest;

public record MemberCreateDto(String email,
                              String password,
                              String nickName,
                              String phone) {

    public static MemberCreateDto from(MemberCreateRequest request) {
        return new MemberCreateDto(
                request.email(),
                request.password(),
                request.nickName(),
                request.phone()
        );
    }
}
