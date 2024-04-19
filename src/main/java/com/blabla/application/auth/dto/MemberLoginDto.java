package com.blabla.application.auth.dto;

import com.blabla.api.auth.request.MemberLoginRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberLoginDto {
    private final String email;
    private final String password;

    public static MemberLoginDto from(MemberLoginRequest request){
        return new MemberLoginDto(
                request.getEmail(),
                request.getPassword()
        );
    }
}
