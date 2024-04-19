package com.blabla.api.auth;

import com.blabla.api.auth.request.MemberCreateRequest;
import com.blabla.api.auth.request.MemberLoginRequest;
import com.blabla.api.auth.response.AuthTokenResponse;
import com.blabla.application.auth.AuthService;
import com.blabla.application.auth.dto.MemberCreateDto;
import com.blabla.application.auth.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberCreateRequest request) {
        authService.register(MemberCreateDto.from(request));
        return  ResponseEntity.created(URI.create("/api/auth/login")).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@RequestBody MemberLoginRequest request) {
        AuthTokenResponse token = authService.login(MemberLoginDto.from(request));
        return ResponseEntity.ok(token);
    }
}
