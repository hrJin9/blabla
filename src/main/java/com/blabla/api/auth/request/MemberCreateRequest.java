package com.blabla.api.auth.request;

public record MemberCreateRequest(String loginId,
                                  String email,
                                  String password,
                                  String nickName,
                                  String phone) {
}

