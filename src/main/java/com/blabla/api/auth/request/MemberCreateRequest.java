package com.blabla.api.auth.request;

public record MemberCreateRequest(String email,
                                  String password,
                                  String nickName,
                                  String phone) {
}

