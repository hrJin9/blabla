package com.blabla.api.auth;

import com.blabla.api.auth.request.MemberCreateRequest;
import com.blabla.api.auth.request.MemberLoginRequest;
import com.blabla.api.auth.response.AuthTokenResponse;
import com.blabla.application.auth.AuthService;
import com.blabla.application.auth.dto.MemberCreateDto;
import com.blabla.application.auth.dto.MemberLoginDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.boot.web.server.Cookie.SameSite.NONE;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {
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

    @GetMapping("/reissue-token")
    public ResponseEntity<AuthTokenResponse> reissueToken(@CookieValue(name = "refresh_token") String refreshToken,
                                                          HttpServletResponse response) {
        AuthTokenResponse token = authService.reissueToken(refreshToken);
        ResponseCookie cookie = ResponseCookie.from("refresh_token", token.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/reissue-token")
                .sameSite(NONE.attributeValue())
                .build();
        response.addHeader(SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(token);
    }
}
