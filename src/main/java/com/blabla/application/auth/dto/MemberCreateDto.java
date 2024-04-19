package com.blabla.application.auth.dto;

import com.blabla.api.auth.request.MemberCreateRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateDto {
    private final String email;
    private final String password;
    private final String nickName;
    private final String phone;

    public static MemberCreateDto from(MemberCreateRequest request) {
        return new MemberCreateDto(
                request.getEmail(),
                request.getPassword(),
                request.getNickName(),
                request.getPhone()
        );
    }
}
