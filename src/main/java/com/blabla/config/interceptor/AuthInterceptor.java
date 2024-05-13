package com.blabla.config.interceptor;

import com.blabla.exception.MemberNotFoundException;
import com.blabla.exception.TokenNotValidException;
import com.blabla.repository.auth.MemberRepository;
import com.blabla.util.BearerParser;
import com.blabla.util.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final TokenGenerator tokenGenerator;
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!BearerParser.isBearerAuthType(authorization)) {
            throw new TokenNotValidException("인증할 수 없는 토큰입니다.");
        }

        String accessToken = BearerParser.parseAuthorization(authorization);
        if (!tokenGenerator.isValidToken(accessToken)) {
            throw new TokenNotValidException("유효하지 않은 토큰입니다.");
        }

        Long memberId = tokenGenerator.extractMemberId(accessToken);
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        return true;
    }
}
